package com.roi.kremlin.authorization;

import com.roi.kremlin.ConsumerSpecification;
import com.roi.kremlin.FunctionSpecification;
import com.roi.kremlin.services.ProducerSpecification;
import com.roi.kremlin.services.ServicesBean;
import com.roi.logger.LoggerBean;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class AuthorizationBean {

    @EJB
    private LoggerBean loggerBean;

    @EJB
    private ServicesBean servicesBean;

    public boolean isAuthorized(String appName, String function, String strToken) {
        try {
            UUID token = UUID.fromString(strToken);
            String callerAppName = findAppFromToken(token);
            if (callerAppName == null) {
                loggerBean.logInputErrorFromClass("Provided token doesnt match any app.",
                        AuthorizationBean.class.toString());
                return false;
            }

            FunctionSpecification providerFunctionSpec = servicesBean.getAppFunction(appName, function);
            boolean hasPermisionToCall = providerFunctionSpec.getAccessibleBy().contains(callerAppName);
            if (!hasPermisionToCall) {
                loggerBean.logInputErrorFromClass("Client is not allowed to call this function.",
                        AuthorizationBean.class.toString());
            }
            return hasPermisionToCall;

        } catch (IllegalArgumentException ex) {
            loggerBean.logInputErrorFromClass("Invalid token format provided.", AuthorizationBean.class.toString());
            return false;
        } catch (NoSuchMethodException ex) {
            loggerBean.logInputErrorFromClass(ex.getMessage(), AuthorizationBean.class.toString());
            return false;
        }
    }

    private String findAppFromToken(UUID token) {
        List<ConsumerSpecification> consSpecifications = servicesBean.getConsumerSpecs();
        boolean found = false;
        String appName = null;
        for (int i = 0; i < consSpecifications.size() && !found; i++) {
            ConsumerSpecification spec = consSpecifications.get(i);
            if (spec.getToken().equals(token)) {
                appName = spec.geName();
                found = true;
            }
        }
        return appName;
    }
}
