package com.roi.security;

import com.roi.models.LoggerBean;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.EJB;

@Stateless
@LocalBean
public class AuthenticationBean {

    private Properties properties;

    @EJB
    private LoggerBean loggerBean;
    
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
            loggerBean.logInputErrorFromClass("Couldnt parse configured token.", AuthenticationBean.class.toString());
            throw new IllegalStateException(e.getMessage());
        }
    }
}
