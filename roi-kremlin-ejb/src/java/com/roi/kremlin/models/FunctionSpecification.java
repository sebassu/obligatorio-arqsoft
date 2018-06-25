package com.roi.kremlin.models;

import java.util.List;
import java.util.Map;

public class FunctionSpecification {

    private String name;
    private String location;
    private CommunicationType type;
    private Map<String, String> parameters;
    private List<String> accessibleBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CommunicationType getType() {
        return type;
    }

    public void setType(CommunicationType type) {
        this.type = type;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public List<String> getAccessibleBy() {
        return accessibleBy;
    }
}
