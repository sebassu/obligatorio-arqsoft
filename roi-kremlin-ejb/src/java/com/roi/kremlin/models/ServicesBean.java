package com.roi.kremlin.models;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import java.util.List;
import javax.annotation.PostConstruct;

@Singleton
@LocalBean
public class ServicesBean {

    private List<AppSpecification> appsSpecifications;
    private Gson gson;

    @PostConstruct
    public void init() {
        gson = new Gson();
    }

    public List<AppSpecification> getAppsSpecs() {
        if (appsSpecifications == null) {
            loadRegisteredApplications();
        }
        return appsSpecifications;
    }

    private void loadRegisteredApplications() {
        try {
            JsonReader reader = new JsonReader(new FileReader("/res/config.txt"));
            AppSpecification[] appSpecs = gson.fromJson(reader, AppSpecification[].class);
            appsSpecifications = Arrays.asList(appSpecs);
        } catch (FileNotFoundException io) {

        }
    }
}
