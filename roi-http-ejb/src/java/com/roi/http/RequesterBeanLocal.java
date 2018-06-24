package com.roi.http;

import javax.ejb.Local;


@Local
public interface RequesterBeanLocal {
    Object sendRequest(Request request);
}
