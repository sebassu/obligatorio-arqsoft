package com.roi.supplying.notification;

import com.roi.http.Request;
import com.roi.http.RequesterBean;
import com.roi.security.AuthenticationBean;
import com.roi.supplying.SupplyOrder;
import java.lang.reflect.Type;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

@Stateful
@LocalBean
public class SupplyOrderNotificationBean {

    
    private static final String KREMLIN_URL = "Kremlin URL";
    
    private String token;
    
    @EJB
    private AuthenticationBean authenticationBean;
    @EJB
    private RequesterBean requesterBean;

    @PostConstruct
    private void init() {
        token = authenticationBean.getToken().toString();
    }


    private SupplyOrderNotification createNotification(SupplyOrder supplyOrder) {
        long orderNumber = supplyOrder.getOrderNumber();
        long servicePointId = supplyOrder.getServicePointId();

        return new SupplyOrderNotification(orderNumber, servicePointId);
    }

    public void notifyCreation(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        
        String method = "POST";
        String url = KREMLIN_URL;
        Type responseType = String.class;
        boolean responseIsList = false;
        Type contentType = SupplyOrderNotification.class;
        Request request = Request.buildRequestWithContent(url, method, responseType,
                responseIsList, token, notification, contentType);
        String responses = (String) requesterBean.sendRequest(request);
    }

    public void notifyModification(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        String method = "PUT";
        String url = KREMLIN_URL + "/" + supplyOrder.getOrderNumber();
        Type responseType = String.class;
        boolean responseIsList = false;
        Type contentType = SupplyOrderNotification.class;
  
        Request request = Request.buildRequestWithContent(url, method, responseType,
                responseIsList, token, notification, contentType);
        String response = (String) requesterBean.sendRequest(request);
    }

    public void notifyRemoval(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        String method = "DELETE";
        String url = KREMLIN_URL + "/" + supplyOrder.getOrderNumber();
        Type responseType = String.class;
        boolean responseIsList = false;
        
        Request request = Request.buildRequestWithoutContent(url, method, responseType, responseIsList, token);
        String response = (String) requesterBean.sendRequest(request);
    }
}
