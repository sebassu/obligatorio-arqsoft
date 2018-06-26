package com.roi.kremlin.models;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

@Singleton
@LocalBean
public class ServicesBean {

    @EJB
    private FormatBean formatBean;

    private List<AppSpecification> appsSpecifications;
    private Gson gson;

    @PostConstruct
    public void init() {
        gson = new Gson();
        loadRegisteredApplications();
    }

    public List<AppSpecification> getAppsSpecs() {
        if(appsSpecifications == null){
            loadRegisteredApplications();
        }
        return appsSpecifications;
    }

    public FunctionSpecification getAppFunction(String appName, String function) throws NoSuchMethodException {
        AppSpecification appSpec = getAppSpecificationByName(appName);
        if (appSpec == null) {
            throw new NoSuchMethodException("App is not specified.");
        }
        FunctionSpecification funSpec = appSpec.getFunSpecificationByName(function);
        if (funSpec == null) {
            throw new NoSuchMethodException("Function not specified for the selected app.");
        }
        return funSpec;
    }

    private AppSpecification getAppSpecificationByName(String appName) {
        boolean found = false;
        AppSpecification appSpec = null;
        for (int i = 0; i < appsSpecifications.size() && !found; i++) {
            AppSpecification spec = appsSpecifications.get(i);
            if (spec.getName().equals(appName)) {
                appSpec = spec;
                found = true;
            }
        }
        return appSpec;
    }

    private void loadRegisteredApplications() {
        try {
            JsonReader reader = new JsonReader(new FileReader("/res/config.txt"));
            AppSpecification[] appSpecs = gson.fromJson(reader, AppSpecification[].class);
            appsSpecifications = Arrays.asList(appSpecs);
            if (!checkFormatsAreValid()) {
                appsSpecifications = null;
                throw new IllegalStateException("Specification file with invalid format.");
            }
        } catch (FileNotFoundException | JsonIOException | JsonSyntaxException io) {
            throw new IllegalStateException("Specification file is invalid: " + io);
        }
    }

    private boolean checkFormatsAreValid() {
        boolean valid = true;
        for (int i = 0; i < appsSpecifications.size() && valid; i++) {
            AppSpecification appSpec = appsSpecifications.get(i);
            for (int j = 0; j < appSpec.getFunctionSpecifications().size() && valid; j++) {
                FunctionSpecification funSpec = appSpec.getFunctionSpecifications().get(j);
                for (int k = 0; k < funSpec.getParameters().size() && valid; k++) {
                    ParameterSpecification paramSpec = funSpec.getParameters().get(k);
                    valid = formatBean.specifiedFormatisValid(paramSpec);
                }
            }
        }
        return valid;
    }
}
