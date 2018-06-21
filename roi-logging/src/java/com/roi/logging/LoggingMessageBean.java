package com.roi.logging;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.log4j.Logger;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/roiLoggingQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")})
public class LoggingMessageBean implements MessageListener {

    public LoggingMessageBean() {
    }

    @Override
    public void onMessage(Message message) {
        Logger logger = Logger.getLogger(LoggingMessageBean.class.getName());
        Throwable dataToLog = getContentsFromMessage(message);
        logger.error("Error", dataToLog);
    }

    private Throwable getContentsFromMessage(Message message) {
        try {
            return message.getBody(Throwable.class);
        } catch (JMSException exception) {
            return new IllegalArgumentException("Ocurrió un error desconocido:"
                    + " no fue posible interpretar el contenido de un mensaje.");
        }
    }
}
