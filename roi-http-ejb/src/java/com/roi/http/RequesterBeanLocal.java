package com.roi.http;

import java.lang.reflect.Type;
import javax.ejb.Local;


@Local
public interface RequesterBeanLocal {
    Object sendRequest(String url, String method, Type responseType, boolean responseIsList, Object content, Type contentType);
    Object sendRequest(String url, String method, Type responseType, boolean responseIsList);
}
