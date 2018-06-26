package com.roi.kremlin.models;

import com.roi.models.LoggerBean;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

    private boolean parametersMatch(List<ParameterSpecification> specifiedParams, JSONObject data) {
        boolean hasParameters = true;
        Iterator<ParameterSpecification> iterator = specifiedParams.iterator();
        while (iterator.hasNext() && hasParameters) {
            ParameterSpecification paramSpec = iterator.next();
            hasParameters = meetsSpecification(data, paramSpec);
        }
        return hasParameters;
    }

    private boolean meetsSpecification(JSONObject data, ParameterSpecification paramSpec) {
        boolean hasParameter = false;
        try {
            if (data.containsKey(paramSpec.getName())) {
                Object value = data.get(paramSpec.getName());
                boolean valueHasCorrectType;

                valueHasCorrectType = Class.forName(paramSpec.getType()).isInstance(value);
                valueHasCorrectFormat = validateFormat(value, paramSpec);

                hasParameter = valueHasCorrectType;
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

    private boolean validateFormat(Object value, ParameterSpecification paramSpec) {
        return true;
//        (Class.forName(paramSpec.getType())).
//        switch (paramType) {
//            case Date.class:
//                
//                break;
//            default:
//                throw new AssertionError();
//        }
    }
}
