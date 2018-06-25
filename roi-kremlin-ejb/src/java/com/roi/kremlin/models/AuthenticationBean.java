package com.roi.kremlin.models;

import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class AuthenticationBean {

    @EJB
    private ServicesBean servicesBean;

    public String identifyApplication(UUID token) {
        String appName = findAppFromToken(token);
        if (appName == null) {
            //TODO not authenticated.
        }
        return appName;
    }

    private String findAppFromToken(UUID token) {
        List<AppSpecification> appsSpecifications = servicesBean.getAppsSpecs();
        boolean found = false;
        String appName = null;
        for (int i = 0; i < appsSpecifications.size() && !found; i++) {
            AppSpecification spec = appsSpecifications.get(i);
            if (spec.getToken().equals(token)) {
                appName = spec.getName();
                found = true;
            }
        }
        return appName;
    }

}
