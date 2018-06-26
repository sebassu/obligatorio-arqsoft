package com.roi.http;

import java.io.Serializable;
import java.lang.reflect.Type;

public class Request implements Serializable {

    public String url;
    public String method;
    public Type responseType;
    public boolean responseIsList;
    public Object content;
    public Type contentType;
    public String token;

    private Request(String url, String method, Type responseType, boolean responseIsList,
            String token, Object content, Type contentType) {
        this.url = url;
        this.method = method;
        this.responseType = responseType;
        this.responseIsList = responseIsList;
        this.token = token;
        this.content = content;
        this.contentType = contentType;
    }

    public static Request buildRequestWithoutContent(String url, String method, Type responseType,
            boolean responseIsList, String token) {
        return new Request(url, method, responseType, responseIsList, token, null, null);
    }

    public static Request buildRequestWithContent(String url, String method, Type responseType,
            boolean responseIsList, String token, Object content, Type contentType) {
        return new Request(url, method, responseType, responseIsList, token, content, contentType);
    }
}
