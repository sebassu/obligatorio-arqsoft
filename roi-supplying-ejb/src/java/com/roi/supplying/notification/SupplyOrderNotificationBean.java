package com.roi.supplying.notification;

import com.roi.http.Request;
import com.roi.http.RequesterBean;
import com.roi.supplying.SupplyOrder;
import com.roi.supplying.security.AuthenticationBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

@Stateful
@LocalBean
public class SupplyOrderNotificationBean {

    @EJB
    private AuthenticationBean authenticationBean;
    @EJB
    private RequesterBean requesterBean;

    @PostConstruct
    private void init() {
        token = authenticationBean.getToken().toString();
    }

    private String token;
    private static final String KREMLIN_URL = "Kremlin URL";

    private SupplyOrderNotification createNotification(SupplyOrder supplyOrder) {
        long orderNumber = supplyOrder.getOrderNumber();
        long servicePointId = supplyOrder.getServicePointId();

        return new SupplyOrderNotification(orderNumber, servicePointId);
    }

    public void notifyCreation(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        Request request = new Request();
        request.method = "POST";
        request.url = KREMLIN_URL;
        request.responseType = String.class;
        request.responseIsList = false;
        request.content = notification;
        request.contentType = SupplyOrderNotification.class;
        String s = (String) requesterBean.sendRequest(request);
    }

    public void notifyModification(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        Request request = new Request();
        request.method = "PUT";
        request.url = KREMLIN_URL + "/" + supplyOrder.getOrderNumber();
        request.responseType = String.class;
        request.responseIsList = false;
        request.content = notification;
        request.contentType = SupplyOrderNotification.class;
        String s = (String) requesterBean.sendRequest(request);
    }

    public void notifyRemoval(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        Request request = new Request();
        request.method = "DELETE";
        request.url = KREMLIN_URL + "/" + supplyOrder.getOrderNumber();
        request.responseType = String.class;
        request.responseIsList = false;
        String s = (String) requesterBean.sendRequest(request);
    }

}
