package com.roi.kremlin.validation;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.roi.kremlin.ParameterSpecification;
import com.roi.kremlin.FunctionSpecification;
import com.roi.kremlin.services.ServicesBean;
import com.roi.logger.LoggerBean;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Stateless
@LocalBean
public class ValidatorBean {

    private Gson gson;

    @EJB
    FormatBean formatBean;

    @EJB
    ServicesBean servicesBean;

    @EJB
    LoggerBean loggerBean;

    @PostConstruct
    public void init() {
        gson = new Gson();
    }

    public boolean isValid(String appName, String function, String content) {
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
        boolean hasParameter = true;
        try {
            if (data.containsKey(paramSpec.getName())) {
                String value = (String) data.get(paramSpec.getName());

                gson.fromJson(value, Class.forName(paramSpec.getType()));

                if (paramSpec.getFormat() != null) {
                    hasParameter = formatBean.validateFormat(value, paramSpec);
                }
            }
        } catch (ClassNotFoundException | JsonSyntaxException ex) {
            loggerBean.logFatalErrorFromMessageClass("Error when validating value type",
                    ValidatorBean.class.toString(), ex);
            return false;
        }
        return hasParameter;
    }

    private JSONObject getJsonfromString(String strJson) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(strJson);
    }
}
