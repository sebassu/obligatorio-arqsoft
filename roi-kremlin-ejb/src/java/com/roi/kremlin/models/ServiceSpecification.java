package com.roi.kremlin.models;

import java.util.List;
import java.util.UUID;

public class ServiceSpecification {

    private String name;
    private UUID token;

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
    
    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
