package com.roi.http;

import javax.ejb.Local;


@Local
public interface RequesterBeanLocal {
    public Object sendRequest(Request request);

    public Object sendPureJson(Request request);
}
