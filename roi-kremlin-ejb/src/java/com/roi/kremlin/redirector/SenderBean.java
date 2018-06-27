package com.roi.kremlin.redirector;

import com.roi.kremlin.redirector.JmsSenderBean;
import com.roi.kremlin.FunctionSpecification;
import com.roi.kremlin.validation.ValidatorBean;
import com.roi.kremlin.services.ServicesBean;
import com.roi.http.Request;
import com.roi.http.RequesterBean;
import com.roi.kremlin.CommunicationType;
import com.roi.logger.LoggerBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.EJB;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
        url = checkAndAddPathParams(url, body);

        Request req = Request.buildRequestWithPlainJson(url, method, String.class, true, null, body);
        requesterBean.sendPureJson(req);
    }

    private void sendJms(FunctionSpecification funSpec, String body) {
        String queueName = funSpec.getLocation();
        jmsSenderBean.attemptToSendMessageToQueue(queueName, body);
    }

    private String checkAndAddPathParams(String url, String body) {
        String modifiedUrl = url;
        try {
            JSONObject jsonData = (JSONObject) (new JSONParser().parse(body));

            Pattern pathParamPattern = Pattern.compile("\\{(.*?)\\}");
            Matcher matcher = pathParamPattern.matcher(url);

            while (matcher.find()) {
                String paramName = matcher.group(1);
                if (!jsonData.containsKey(paramName)) {
                    throw new IllegalArgumentException("specified query param was not provided");
                }
                String value = jsonData.get(paramName).toString();
                matcher.replaceFirst(value);
            }
        } catch (ParseException ex) {
            loggerBean.logInputErrorFromClass("Couldnt parse provided JSON: " + ex, SenderBean.class.toString());
        }
        return modifiedUrl;
    }
}
