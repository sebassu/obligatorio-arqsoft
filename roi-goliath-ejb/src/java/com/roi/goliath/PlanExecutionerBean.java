package com.roi.goliath;

import com.roi.logger.LoggerBean;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/roiGoliathQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")})
public class PlanExecutionerBean implements MessageListener {

    @EJB
    private LoggerBean logger;

    public PlanExecutionerBean() {
    }

    @Override
    public void onMessage(Message data) {
        try {
            TextMessage stringData = (TextMessage) data;
            executePlan(stringData.getText());
        } catch (JMSException exception) {
            String message = "Ocurrió un error desconocido: no fue posible"
                    + " interpretar el contenido de un mensaje.";
            String originClass = PlanExecutionerBean.class.getName();
            logger.logFatalErrorFromMessageClass(message, originClass, exception);
        }
    }

    private void executePlan(String planData) {
        String message = "Se ejecutó en Goliath el plan de datos: " + planData;
        String originClass = PlanExecutionerBean.class.getName();
        logger.logInformationMessageFromClass(message, originClass);
    }
}
