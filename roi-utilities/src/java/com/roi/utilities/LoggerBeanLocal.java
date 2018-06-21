package com.roi.utilities;

import javax.ejb.Local;

@Local
public interface LoggerBeanLocal {

    void logError(Throwable parameter);
}
