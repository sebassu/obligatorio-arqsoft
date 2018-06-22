package com.roi.utilities;

import java.io.Serializable;

public class LoggingData implements Serializable {

    public final String message;
    public final String originClass;
    public final LogType type;
    public final Throwable errorData;

    private LoggingData(String messageToSet, String originClassToSet,
            LogType typeToSet) {
        message = messageToSet;
        originClass = originClassToSet;
        type = typeToSet;
        errorData = null;
    }

    private LoggingData(String messageToSet, String originClassToSet,
            Throwable errorDataToSet) {
        message = messageToSet;
        originClass = originClassToSet;
        type = LogType.FATAL_ERROR;
        errorData = errorDataToSet;
    }

    public static LoggingData forInformationLog(String message,
            String originClass) {
        return new LoggingData(message, originClass, LogType.INFORMATION);
    }

    public static LoggingData forInputErrorLog(String message,
            String originClass) {
        return new LoggingData(message, originClass, LogType.INPUT_ERROR);
    }

    public static LoggingData forFatalErrorLog(String message,
            String originClass, Throwable errorData) {
        return new LoggingData(message, originClass, errorData);
    }
}
