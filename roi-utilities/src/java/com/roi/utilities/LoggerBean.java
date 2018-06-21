package com.roi.utilities;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

@Stateless
public class LoggerBean implements LoggerBeanLocal {

    @Resource(lookup = "jms/roiConnectionFactory")
    private ConnectionFactory factory;
    @Resource(lookup = "jms/roiLoggingQueue")
    private Queue loggingQueue;

    @Override
    public void logError(Throwable errorToLog) {
        try {
            sendMessageToLoggingQueue(errorToLog);
        } catch (JMSException exception) {
            Logger.getLogger(LoggerBean.class.getName()).log(Level.SEVERE,
                    "Error al enviar un mensaje a la cola de logging.", exception);
        }
    }

    private void sendMessageToLoggingQueue(Throwable errorToLog) throws JMSException {
        Connection connection = factory.createConnection();
        Session session = connection.createSession();
        ObjectMessage messageToSend = session.createObjectMessage(errorToLog);
        MessageProducer producer = session.createProducer((Destination) loggingQueue);
        producer.send(messageToSend);
    }
}
