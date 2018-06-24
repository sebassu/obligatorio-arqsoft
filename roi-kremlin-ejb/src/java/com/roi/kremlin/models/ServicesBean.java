package com.roi.kremlin.models;

import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Singleton
@LocalBean
public class ServicesBean {

    private Map<String, UUID> tokens;
    private List<ServiceSpecification> servicesSpecifications;

    public String identifyApplication(UUID token) {
        String appName = findAppFromToken(token);
        if (appName == null) {
            //TODO not authenticated.
        }
        return appName;
    }

    private String findAppFromToken(UUID token) {
        boolean found = false;
        String appName = null;
        for (int i = 0; i < servicesSpecifications.size() && !found; i++) {
            ServiceSpecification spec = servicesSpecifications.get(i);
            if (spec.getToken().equals(token)) {
                appName = spec.getName();
                found = true;
            }
        }
        return appName;
    }
}
