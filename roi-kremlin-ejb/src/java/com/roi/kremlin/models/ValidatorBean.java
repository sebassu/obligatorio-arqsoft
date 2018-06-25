package com.roi.kremlin.models;

import com.roi.models.LoggerBean;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Stateless
@LocalBean
public class ValidatorBean {

    @EJB
    ServicesBean servicesBean;

    @EJB
    LoggerBean loggerBean;

    public boolean isValidCall(String appName, String function, String content) {
        FunctionSpecification funSpec;
        try {
            funSpec = servicesBean.getAppFunction(appName, function);
            JSONObject jsonData;
            jsonData = getJsonfromString(content);
            return parametersMatch(funSpec.getParameters(), jsonData);
            
        } catch (NoSuchMethodException ex) {
            loggerBean.logInputErrorFromClass(ex.getMessage(),
                    ValidatorBean.class.toString());
            return false;
        } catch (ParseException ex) {
            loggerBean.logInputErrorFromClass("Error when parsing provided parameters.",
                    ValidatorBean.class.toString());
            return false;
        } 
    }

    private boolean parametersMatch(Map<String, String> specifiedParams, JSONObject data) {
        boolean hasParameters = true;
        Iterator<String> iterator = specifiedParams.keySet().iterator();
        while (iterator.hasNext() && hasParameters) {
            String paramName = iterator.next();
            String paramType = specifiedParams.get(paramName);
            hasParameters = hasParameter(data, paramName, paramType);
        }
        return hasParameters;
    }

    private boolean hasParameter(JSONObject data, String paramName, String paramType) {
        boolean hasParameter = false;
        try {
            if (data.containsKey(paramName)) {
                Object value = data.get(paramName);
                boolean valueIsCorrectType;

                valueIsCorrectType = Class.forName(paramType).isInstance(value);

                hasParameter = valueIsCorrectType;
            }
        } catch (ClassNotFoundException ex) {
            loggerBean.logFatalErrorFromMessageClass("Error when validating value type",
                    ValidatorBean.class.toString(), ex);
        }
        return hasParameter;
    }

    private JSONObject getJsonfromString(String strJson) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(strJson);
    }
}
