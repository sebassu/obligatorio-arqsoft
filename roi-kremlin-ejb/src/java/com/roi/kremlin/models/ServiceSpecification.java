package com.roi.kremlin.models;

import java.util.List;

public class ServiceSpecification {

    private String name;
    private List<FunctionSpecification> functionSpecifications;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FunctionSpecification> getFunctionSpecifications() {
        return functionSpecifications;
    }

    public void setFunctionSpecifications(List<FunctionSpecification> functionSpecifications) {
        this.functionSpecifications = functionSpecifications;
    }
}
