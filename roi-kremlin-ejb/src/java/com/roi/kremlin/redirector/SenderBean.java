package com.roi.kremlin.redirector;

import com.roi.kremlin.redirector.JmsSenderBean;
import com.roi.kremlin.FunctionSpecification;
import com.roi.kremlin.validation.ValidatorBean;
import com.roi.kremlin.services.ServicesBean;
import com.roi.http.Request;
import com.roi.http.RequesterBean;
import com.roi.kremlin.CommunicationType;
import com.roi.logger.LoggerBean;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.EJB;

@Stateless
@LocalBean
public class SenderBean {

    @EJB
    private ServicesBean servicesBean;

    @EJB
    private LoggerBean loggerBean;

    @EJB
    private JmsSenderBean jmsSenderBean;

    @EJB
    private RequesterBean requesterBean;

    public void send(String appName, String function, String body) {
        FunctionSpecification funSpec;
        try {
            funSpec = servicesBean.getAppFunction(appName, function);
            sendWithAnyCommunicationType(funSpec, body);
        } catch (NoSuchMethodException ex) {
            loggerBean.logInputErrorFromClass(ex.getMessage(),
                    ValidatorBean.class.toString());
        }
    }

    private void sendWithAnyCommunicationType(FunctionSpecification funSpec, String body) {
        CommunicationType commType = funSpec.getType();
        if (commType.equals(CommunicationType.WEB_REST_API)) {
            sendRest(funSpec, body);
        } else if (commType.equals(CommunicationType.JAVA_MESSAGE_QUEUES)) {
            sendJms(funSpec, body);
        }
    }

    private void sendRest(FunctionSpecification funSpec, String body) {
        String[] location = funSpec.getLocation().split("%");
        String method = location[0];
        String url = location[1];
        Request req = Request.buildRequestWithPlainJson(url, method, String.class, true, null, body);
        requesterBean.sendPureJson(req);
    }

    private void sendJms(FunctionSpecification funSpec, String body) {
        String queueName = funSpec.getLocation();
        jmsSenderBean.attemptToSendMessageToQueue(queueName, body);
    }
}
