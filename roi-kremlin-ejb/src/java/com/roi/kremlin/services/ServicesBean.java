package com.roi.kremlin.services;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.roi.kremlin.ConsumerSpecification;
import com.roi.kremlin.validation.FormatBean;
import com.roi.kremlin.FunctionSpecification;
import com.roi.kremlin.ParameterSpecification;
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

    private static final String CONSUMERS_FILE_PATH = "/res/consumers.txt";
    private static final String PRODUCERS_FILE_PATH = "/res/producers.txt";

    @EJB
    private FormatBean formatBean;

    private List<ProducerSpecification> producersSpecifications;
    private List<ConsumerSpecification> consumersSpecifications;
    private Gson gson;

    @PostConstruct
    public void init() {
        gson = new Gson();
        loadRegisteredApplications();
    }

    public List<ConsumerSpecification> getConsumerSpecs() {
        if (consumersSpecifications == null) {
            loadRegisteredApplications();
        }
        return consumersSpecifications;
    }
    
    public List<ProducerSpecification> getProducersSpecs() {
        if (producersSpecifications == null) {
            loadRegisteredApplications();
        }
        return producersSpecifications;
    }

    public FunctionSpecification getAppFunction(String appName, String function) throws NoSuchMethodException {
        ProducerSpecification appSpec = getAppSpecificationByName(appName);
        if (appSpec == null) {
            throw new NoSuchMethodException("App is not specified.");
        }
        FunctionSpecification funSpec = appSpec.getFunSpecificationByName(function);
        if (funSpec == null) {
            throw new NoSuchMethodException("Function not specified for the selected app.");
        }
        return funSpec;
    }

    private ProducerSpecification getAppSpecificationByName(String appName) {
        boolean found = false;
        ProducerSpecification appSpec = null;
        for (int i = 0; i < producersSpecifications.size() && !found; i++) {
            ProducerSpecification spec = producersSpecifications.get(i);
            if (spec.getName().equals(appName)) {
                appSpec = spec;
                found = true;
            }
        }
        return appSpec;
    }

    private void loadRegisteredApplications() {
        loadProducers();
        loadConsumers();
    }

    private void loadProducers() {
        try {
            JsonReader reader = new JsonReader(new FileReader(PRODUCERS_FILE_PATH));
            ProducerSpecification[] appSpecs = gson.fromJson(reader, ProducerSpecification[].class);
            producersSpecifications = Arrays.asList(appSpecs);
            if (!checkFormatsAreValid()) {
                producersSpecifications = null;
                throw new IllegalStateException("Specification file with invalid format.");
            }
        } catch (FileNotFoundException | JsonIOException | JsonSyntaxException io) {
            throw new IllegalStateException("Specification file is invalid: " + io);
        }
    }

    private void loadConsumers() {
        try {
            JsonReader reader = new JsonReader(new FileReader(CONSUMERS_FILE_PATH));
            ConsumerSpecification[] conSpecs = gson.fromJson(reader, ConsumerSpecification[].class);
            consumersSpecifications = Arrays.asList(conSpecs);
        } catch (FileNotFoundException | JsonIOException | JsonSyntaxException io) {
            throw new IllegalStateException("Specification file is invalid: " + io);
        }
    }

    private boolean checkFormatsAreValid() {
        boolean valid = true;
        for (int i = 0; i < producersSpecifications.size() && valid; i++) {
            ProducerSpecification appSpec = producersSpecifications.get(i);
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
