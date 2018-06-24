package com.roi.supplying.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class AuthenticationBean {

    private Properties properties;

    @PostConstruct
    public void init() {
        properties = new Properties();
    }

    public UUID getToken() {
        try (FileInputStream in = new FileInputStream("/res/config.txt")) {
            properties.load(in);
            String strToken = properties.getProperty("token");
            return UUID.fromString(strToken);
        } catch (IOException | IllegalArgumentException e) {
            // TODO log and throw new Exception maybe?
            e.printStackTrace();
            return null;
        }
    }
}
