package com.roi.kremlin.services;

import com.roi.kremlin.FunctionSpecification;
import java.util.List;
import java.util.UUID;

public class ProducerSpecification {

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
    
    public FunctionSpecification getFunSpecificationByName(String functionName) {
        boolean found = false;
        FunctionSpecification funSpec = null;
        for (int i = 0; i < this.functionSpecifications.size() && !found; i++) {
            FunctionSpecification spec = this.functionSpecifications.get(i);
            if (spec.getName().equals(functionName)) {
                funSpec = spec;
                found = true;
            }
        }
        return funSpec;
    }
}
