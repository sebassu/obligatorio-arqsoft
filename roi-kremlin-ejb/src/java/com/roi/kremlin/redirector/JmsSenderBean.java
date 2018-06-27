package com.roi.kremlin.redirector;

import com.roi.logger.LoggerBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateless
@LocalBean
public class JmsSenderBean {

    @Resource(lookup = "jms/roiConnectionFactory")
    private ConnectionFactory factory;

    public void attemptToSendMessageToQueue(String queueName, String dataToSend) {
        try {

            sendMessageToQueue(queueName, dataToSend);
        } catch (JMSException | NamingException exception) {
            Logger.getLogger(LoggerBean.class.getName()).log(Level.SEVERE,
                    "Error al enviar un mensaje a la cola de logging.", exception);
        }
    }

    private void sendMessageToQueue(String queueName, String dataToSend) throws JMSException, NamingException {
        Context jndiContext = new InitialContext();

        Connection connection = factory.createConnection();
        Session session = connection.createSession();
        TextMessage messageToSend = session.createTextMessage(dataToSend);
        Destination queue = (Destination) jndiContext.lookup(queueName);
        try (JMSContext context = factory.createContext()) {
            context.createProducer().send(queue, messageToSend);
        }
    }
}
