package ics.book.tracker.main;

import ics.book.tracker.publish.PublishToCloud;
import ics.book.tracker.subscribe.SubscribeToCloud;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.cloud.messaging.*;
import oracle.cloud.messaging.client.*;
import oracle.cloud.messaging.client.admin.properties.QueueProperties;
import oracle.cloud.messaging.common.*;

/**
 *
 * @author Robert van Mölken [robert_m]
 */
public class FlightPositionTracker {

    private static final Logger LOGGER = Logger.getLogger(FlightPositionTracker.class.getName());
    private static final String CONFIGFILE = "resources/instance.properties";
    
    private static final String OVERRIDEPUBLISHQ = "overridePublishQ";
    private static final String OVERRIDESUBSCRIBEQ = "overrideSubscribeQ";
    private static final String PUBLISHOFF = PublishToCloud.QUEUENAME+"OFF";
    private static final String SUBSCRIBEOFF = SubscribeToCloud.QUEUENAME+"OFF";
    
    private boolean setup = false;
    
    
    public MessagingService getMessagingService() throws Exception {
        MessagingServiceFactory factory = MessagingServiceFactory.getInstance();
        
        Properties prop = new Properties();
	InputStream input = this.getClass().getClassLoader().getResourceAsStream(CONFIGFILE);
        prop.load(input);
        
        String serviceUrl = prop.getProperty("serviceUrl");
        String username = prop.getProperty("username");
        String password = prop.getProperty("password");

        LOGGER.log(Level.INFO, "Will use Service: {0}", serviceUrl);
        
        Namespace namespace = new MessagingServiceNamespace(serviceUrl);
        LOGGER.log(Level.INFO, "Built Namespace");
        Credentials credentials = new MessagingServiceCredentials(username, password);
        LOGGER.log(Level.INFO, "Built Credentials");
        MessagingService service = factory.getMessagingService(namespace, credentials);
        LOGGER.log(Level.INFO, "Established Service");        
        
        return service;
    }

    private boolean isQueueAvailable(MessagingService service, String queueName) {
        try {
            QueueProperties props = service.getQueueProperties(queueName);
            LOGGER.log(Level.INFO, "Status: {0}", props.getStatus());
            return true;
        } catch (MessagingException ex) {
            if ("oracle.cloud.messaging.common.DestinationNotFoundException".equals(ex.getClass().getName())) {
                LOGGER.log(Level.WARNING, "Queue {0} does not yet exist", queueName);
            } else {
                LOGGER.log(Level.SEVERE, "Unknown Exception when checking on " + queueName, ex);
            }
            return false;
        }
    }

    private void createQueue(MessagingService service, String queueName) throws MessagingException {
        LOGGER.log(Level.INFO, "Creating queue {0} for given instance", queueName);
        service.createQueue(queueName);
        LOGGER.log(Level.INFO, "Queue '{0}' succesful created", queueName);
    }

    private String getQueueName (String overideName, String defaultName) throws Exception
    {
        Properties prop = new Properties();
	InputStream input = this.getClass().getClassLoader().getResourceAsStream(CONFIGFILE);
        prop.load(input);
        String qName = null;

        qName = prop.getProperty(overideName);
        if ((qName == null) || (qName.length() == 0))
        {
            qName = defaultName;
        }        
        
        LOGGER.log(Level.INFO, "Queue Name: {0}", qName);
        
        return qName;
    }

        private boolean isShowFullMessage () throws Exception
    {
        Properties prop = new Properties();
	InputStream input = this.getClass().getClassLoader().getResourceAsStream(CONFIGFILE);
        prop.load(input);
        String display = null;
        boolean switchOn = false;

        display = prop.getProperty("DisplayFullMessage");
        if ((display != null) && (display.equalsIgnoreCase("true")))
        {
            switchOn = false;
            LOGGER.log(Level.INFO, "Will display full message");        
        }        
                
        return switchOn;
    } 
    
    private boolean isProcessOn (String overideName) throws Exception
    {
        Properties prop = new Properties();
	InputStream input = this.getClass().getClassLoader().getResourceAsStream(CONFIGFILE);
        prop.load(input);
        String off = null;
        boolean switchOn = true;

        off = prop.getProperty(overideName);
        if (off != null)
        {
            switchOn = false;
            LOGGER.log(Level.INFO, "Queue Name: {0} processing is OFF", overideName);        
        }        
                
        return switchOn;
    }    
    
    public void setup() {
        try {
            MessagingService service = getMessagingService();
            if (!isQueueAvailable(service, getQueueName (OVERRIDEPUBLISHQ, PublishToCloud.QUEUENAME))) {
                createQueue(service, getQueueName (OVERRIDEPUBLISHQ, PublishToCloud.QUEUENAME));
            }
            if (!isQueueAvailable(service, getQueueName (OVERRIDESUBSCRIBEQ, SubscribeToCloud.QUEUENAME))) {
                createQueue(service, getQueueName (OVERRIDESUBSCRIBEQ, SubscribeToCloud.QUEUENAME));
            }
            setup = true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } 
    }
    
    public boolean isSetup() {
        return setup;
    }
    
    public void runInBackground() {
        try {
            PublishToCloud R1 = null;
            SubscribeToCloud R2 = null;
            
            if (isProcessOn(PUBLISHOFF))
            {
                R1 = new PublishToCloud(PublishToCloud.QUEUENAME, getMessagingService(),getQueueName (OVERRIDEPUBLISHQ, PublishToCloud.QUEUENAME));
                R1.start();            
            }
            
            if (isProcessOn(SUBSCRIBEOFF))
            {
                R2 = new SubscribeToCloud(SubscribeToCloud.QUEUENAME, getMessagingService(), getQueueName (OVERRIDESUBSCRIBEQ, SubscribeToCloud.QUEUENAME), isShowFullMessage());
                R2.start();
            }
            
            try {
                LOGGER.info("Waiting for threads to finish.");
                if (R1 != null)
                {
                    R1.t.join();
                }
                
                if (R2 != null)
                {
                    R2.t.join();
                }
            } catch (InterruptedException e) {
                LOGGER.severe("Main thread Interrupted");
            }                
        } catch(Exception ex) {
            LOGGER.log(Level.SEVERE, "Error connecting to Cloud", ex);
        }
        LOGGER.info("Main thread exiting.");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FlightPositionTracker tracker = new FlightPositionTracker();
        tracker.setup();

        if (tracker.isSetup()) {        
            tracker.runInBackground();
        }
    }
}
