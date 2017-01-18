package ics.book.tracker.publish;

import ics.book.tracker.entities.FlightPathUpdate;
import ics.book.tracker.entities.ProcessedResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import oracle.cloud.messaging.MessagingException;
import oracle.cloud.messaging.client.MessagingService;

/**
 *
 * @author Robert van Mölken [robert_m]
 */
public class PublishToCloud implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(PublishToCloud.class.getName());

    public Thread t;
    private final String threadName;
    private boolean suspended = true;
    private final MessagingService msgService;
    private final List<String[]> csvRows;
    public final static String QUEUENAME = "TrackerQueue";
    private String qName = QUEUENAME;

    public PublishToCloud(String name, MessagingService service, String QueueName) {
        threadName = name;
        qName = QueueName;
        msgService = service;
        csvRows = readCSV();
        LOGGER.log(Level.INFO, "Creating {0}", threadName);
	}

    public String activateTracker(String activate ) {
         if(activate != null && activate.equals("true")) {
              resume();
              return "Tracker resumed publishing messages to "+qName;
          } else {
              suspend();
              return "Tracker suspended publishing messages to "+qName;
          }
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Running {0}", threadName);
        activateTracker("true");
        try {
            for (int i = 1; i <= csvRows.size(); i++) {
                synchronized (this) {
                    while (suspended) {
                        wait();
                    }
                }
                String[] pathRow = csvRows.get(i);
                String pathMsg = generateXml(pathRow, i);

                LOGGER.log(Level.INFO, "{0}: {1}", new Object[]{threadName, pathMsg});
                try {
                    sendMessage(pathMsg);
                } catch (MessagingException | JMSException ex) {
                    LOGGER.log(Level.SEVERE, threadName+": Message could not be published to cloud", ex);
                }

                // Let the thread sleep for a while.
                Thread.sleep(30000);
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.INFO, "{0} interrupted.", threadName);
        }
        LOGGER.log(Level.INFO, "{0} exiting.", threadName);
    }

    public void start() {
        LOGGER.log(Level.INFO, "Starting {0}", threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public void suspend() {
        suspended = true;
    }

    public synchronized void resume() {
        suspended = false;
        notify();
    }

    private List<String[]> readCSV() {
        String csvFile = "/resources/KL605_AMS_SFO.csv";
        String line;
        String cvsSplitBy = ",";
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(csvFile)))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] path = line.split(cvsSplitBy);
                rows.add(path);
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

        return rows;
    }

    private String generateXml(String[] pathRow, int index) {
        FlightPathUpdate path = new FlightPathUpdate();
        path.setPathID("KL1620-"+index);
        path.setTime(pathRow[3]);
        path.setLatitude(Float.parseFloat(pathRow[0]));
        path.setLongitude(Float.parseFloat(pathRow[1]));

        ProcessedResult result = new ProcessedResult();
        result.setPathID("KL1620-"+index);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ProcessedResult.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            jaxbMarshaller.marshal(result, sw);
            return sw.toString();
        } catch (JAXBException e) {
          LOGGER.log(Level.SEVERE, null, e);
        }

        return "";
    }

    private void sendMessage(String txtMessage) throws MessagingException, JMSException {
        LOGGER.log(Level.INFO, "{0}: Publishing message to {1}", new Object[]{threadName, QUEUENAME});
        ConnectionFactory cf = msgService.getConnectionFactory();
        Connection conn = cf.createConnection();
        conn.start();
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(qName);
        MessageProducer producer = session.createProducer(queue);
        Message message = session.createTextMessage(txtMessage);
        producer.send(message);
        LOGGER.log(Level.INFO, "{0}: Message to {1} succesfully published", new Object[]{threadName, QUEUENAME});
        conn.stop();
        conn.close();
    }
}
