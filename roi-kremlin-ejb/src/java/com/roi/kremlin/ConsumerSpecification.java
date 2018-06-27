package com.roi.kremlin;

import java.util.UUID;

public class ConsumerSpecification {
    private String name;

    public String geName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
    private UUID token;
}
