package com.roi.logger;

import com.roi.logger.LoggingData;
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
public class LoggerBean {

    @Resource(lookup = "jms/roiConnectionFactory")
    private ConnectionFactory factory;
    @Resource(lookup = "jms/roiLoggingQueue")
    private Queue loggingQueue;

    public void logInformationMessageFromClass(String logMessage,
            String originClass) {
        LoggingData toLog = LoggingData.forInformationLog(logMessage,
                originClass);
        attemptToSendMessageToLoggingQueue(toLog);
    }

    public void logInputErrorFromClass(String logMessage, String originClass) {
        LoggingData toLog = LoggingData.forInputErrorLog(logMessage,
                originClass);
        attemptToSendMessageToLoggingQueue(toLog);
    }

    public void logFatalErrorFromMessageClass(String logMessage,
            String originClass, Throwable errorToLog) {
        LoggingData toLog = LoggingData.forFatalErrorLog(logMessage,
                originClass, errorToLog);
        attemptToSendMessageToLoggingQueue(toLog);
    }

    private void attemptToSendMessageToLoggingQueue(LoggingData dataToLog) {
        try {
            sendMessageToLoggingQueue(dataToLog);
        } catch (JMSException exception) {
            Logger.getLogger(LoggerBean.class.getName()).log(Level.SEVERE,
                    "Error al enviar un mensaje a la cola de logging.", exception);
        }
    }

    private void sendMessageToLoggingQueue(LoggingData dataToLog) throws JMSException {
        Connection connection = factory.createConnection();
        Session session = connection.createSession();
        ObjectMessage messageToSend = session.createObjectMessage(dataToLog);
        MessageProducer producer = session.createProducer((Destination) loggingQueue);
        producer.send(messageToSend);
    }
}
