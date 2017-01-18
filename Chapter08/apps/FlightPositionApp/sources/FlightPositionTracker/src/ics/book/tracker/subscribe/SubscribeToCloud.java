package ics.book.tracker.subscribe;

import ics.book.tracker.entities.ProcessedResult;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import oracle.cloud.messaging.MessagingException;
import oracle.cloud.messaging.client.MessagingService;

/**
 *
 * @author Robert van Mölken [robert_m]
 */
public class SubscribeToCloud implements Runnable {

private static final Logger LOGGER = Logger.getLogger(SubscribeToCloud.class.getName());

    public Thread t;
    private final String threadName;
    boolean suspended = false;
    private final MessagingService msgService;
    public final static String QUEUENAME = "ProcessedQueue";
    private String qName = QUEUENAME;
    private boolean DisplayAllMessage = false;

    public SubscribeToCloud(String name, MessagingService service, String queueName, boolean showFullMessage) 
 {
        threadName = name;
        qName = queueName;
        msgService = service;
        LOGGER.log(Level.INFO, "Creating {0}", threadName);
        DisplayAllMessage = showFullMessage;
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Running {0}", threadName);
        try {
            while(true) {
                synchronized (this) {
                    while (suspended) {
                        wait();
                    }
                }

                try {
                    Message message = readMessage();
                    if (message != null) {
                        if (message instanceof TextMessage) {
                            String txtMessage = ((TextMessage) message).getText();
                            ProcessedResult result = processResult(txtMessage);
                            if(DisplayAllMessage)
                            {
                                LOGGER.log(Level.INFO, "{0}: Response received for {1} message is:\n {2}", new Object[]{threadName, result.getPathID(), txtMessage});
                            } else {
                                LOGGER.log(Level.INFO, "{0}: Response received for {1}", new Object[]{threadName, result.getPathID()});
                            }
                            
                        } else {
                            LOGGER.log(Level.INFO, "{0}: A message not of type TextMessage was received", threadName);
                        }
                    } else {
                        LOGGER.log(Level.INFO, "{0}: No message on queue", threadName);
                    }
                } catch (MessagingException | JMSException ex) {
                    LOGGER.log(Level.SEVERE, threadName+": Messages could not be fetched from cloud", ex);
                }

                // Let the thread sleep for a while.
                Thread.sleep(3000);
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

    private ProcessedResult processResult(String message) {
        ProcessedResult result = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ProcessedResult.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(message);
            result = (ProcessedResult) unmarshaller.unmarshal(reader);

        } catch (JAXBException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

        return result;
    }

    private Message readMessage() throws MessagingException, JMSException {
        LOGGER.log(Level.INFO, "{0}: Reading message from {1}", new Object[]{threadName, qName});
        ConnectionFactory cf = msgService.getConnectionFactory();
        Connection conn = cf.createConnection();
        conn.start();
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(qName);
        MessageConsumer consumer = session.createConsumer(queue);
        Message message = consumer.receive(1000);
        conn.stop();
        conn.close();

        return message;
    }
}
