package com.roi.models;

import javax.ejb.Local;

@Local
public interface LoggerBeanLocal {

    void logInformationMessageFromClass(String logMessage, String originClass);

    void logInputErrorFromClass(String logMessage, String originClass);

    void logFatalErrorFromMessageClass(String logMessage, String originClass,
            Throwable parameter);
}
