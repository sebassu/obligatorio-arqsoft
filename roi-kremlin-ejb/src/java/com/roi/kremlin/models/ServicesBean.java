package com.roi.kremlin.models;

import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import java.util.List;
import java.util.Map;

@Singleton
@LocalBean
public class ServicesBean {

    private Map<String, String> tokens;
    private List<FunctionSpecification> availableFunctions;

    public void registerNewServiceProvider(ServiceSpecification data) {
        availableFunctions.addAll(data.getFunctionSpecifications());
    }
}
