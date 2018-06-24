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
    
    //TODO Create two constructors, make all final
}
