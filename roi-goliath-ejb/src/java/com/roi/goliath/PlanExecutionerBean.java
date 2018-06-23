package com.roi.goliath;

import com.google.gson.Gson;
import com.roi.models.LoggerBeanLocal;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/roiGoliathQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")})
public class PlanExecutionerBean implements MessageListener {

    @EJB
    private LoggerBeanLocal logger;
    private Gson gson;

    public PlanExecutionerBean() {
    }

    @Override
    public void onMessage(Message data) {
        try {
            SupplyPlan toApprove = data.getBody(SupplyPlan.class);
            executePlan(toApprove);
        } catch (JMSException exception) {
            String message = "Ocurrió un error desconocido: no fue posible"
                    + " interpretar el contenido de un mensaje.";
            String originClass = PlanExecutionerBean.class.getName();
            logger.logFatalErrorFromMessageClass(message, originClass, exception);
        }
    }

    private void executePlan(SupplyPlan toApprove) {
        String planData = gson.toJson(toApprove);
        String message = "Se ejecutó en Goliath el plan de datos: " + planData;
        String originClass = PlanExecutionerBean.class.getName();
        logger.logInformationMessageFromClass(message, originClass);
    }
}
