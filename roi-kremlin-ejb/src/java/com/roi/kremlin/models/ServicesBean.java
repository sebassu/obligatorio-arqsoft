package com.roi.kremlin.models;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;

@Singleton
@LocalBean
public class ServicesBean {

    private List<AppSpecification> servicesSpecifications;
    private Gson gson;

    @PostConstruct
    public void init() {
        gson = new Gson();
        loadRegisteredApplications();
    }

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
            AppSpecification spec = servicesSpecifications.get(i);
            if (spec.getToken().equals(token)) {
                appName = spec.getName();
                found = true;
            }
        }
        return appName;
    }

    private void loadRegisteredApplications() {
        try {
            JsonReader reader = new JsonReader(new FileReader("/res/config.txt"));
            AppSpecification[] appSpecs = gson.fromJson(reader, AppSpecification[].class);
            servicesSpecifications = Arrays.asList(appSpecs);
        } catch (FileNotFoundException io) {

        }
    }
}
