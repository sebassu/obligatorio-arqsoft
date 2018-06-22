package com.roi.logging;

import com.roi.utilities.LoggingData;
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
        LoggingData toLog = getContentsFromMessage(message);
        performDataLogging(toLog);
    }

    private void performDataLogging(LoggingData dataToLog) {
        Logger logger = Logger.getLogger(dataToLog.originClass);
        switch (dataToLog.type) {
            case INFORMATION:
                logger.info(dataToLog.message);
                break;
            case INPUT_ERROR:
                logger.warn(dataToLog.message);
                break;
            case FATAL_ERROR:
                logger.error(dataToLog.message, dataToLog.errorData);
                break;
            default:
                logger.error("Se obtuvo un mensaje para logging de tipo "
                        + "desconocido.");
                break;
        }
    }

    private LoggingData getContentsFromMessage(Message data) {
        try {
            return data.getBody(LoggingData.class);
        } catch (JMSException exception) {
            String message = "Ocurrió un error desconocido: no fue posible"
                    + " interpretar el contenido de un mensaje.";
            String originClass = LoggingMessageBean.class.getName();
            return LoggingData.forFatalErrorLog(message, originClass, exception);
        }
    }
}
