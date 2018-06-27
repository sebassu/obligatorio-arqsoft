package com.roi.supplying.notification;

import com.roi.http.Request;
import com.roi.http.RequesterBean;
import com.roi.logger.LoggerBean;
import com.roi.security.AuthenticationBean;
import com.roi.supplying.persistence.SupplyOrder;
import java.lang.reflect.Type;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

@Stateful
@LocalBean
public class SupplyOrderNotificationBean implements ISupplyOrderNotificationBean {

    private String KREMLIN_URL;

    private String token;

    @EJB
    private AuthenticationBean authenticationBean;
    @EJB
    private RequesterBean requesterBean;
    @EJB
    private LoggerBean loggerBean;

    @PostConstruct
    private void init() {
        token = authenticationBean.getToken().toString();
    }

    private SupplyOrderNotification createNotification(SupplyOrder supplyOrder) {
        long orderNumber = supplyOrder.getOrderNumber();
        long servicePointId = supplyOrder.getServicePointId();

        return new SupplyOrderNotification(orderNumber, servicePointId);
    }

    @Override
    public void notifyCreation(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);

        String method = "POST";
        String url = getKremlinUrl();
        Type responseType = String.class;
        boolean responseIsList = false;
        Type contentType = SupplyOrderNotification.class;
        Request request = Request.buildRequestWithContent(url, method, responseType,
                responseIsList, token, notification, contentType);
        String response = (String) requesterBean.sendRequest(request);
        loggerBean.logInformationMessageFromClass(response, SupplyOrderNotificationBean.class.toString());
    }

    @Override
    public void notifyModification(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        String method = "PUT";
        String url = getKremlinUrl() + "/" + supplyOrder.getOrderNumber();
        Type responseType = String.class;
        boolean responseIsList = false;
        Type contentType = SupplyOrderNotification.class;

        Request request = Request.buildRequestWithContent(url, method, responseType,
                responseIsList, token, notification, contentType);
        String response = (String) requesterBean.sendRequest(request);
        loggerBean.logInformationMessageFromClass(response, SupplyOrderNotificationBean.class.toString());
    }

    @Override
    public void notifyRemoval(long orderNumber) {
        String method = "DELETE";
        String url = getKremlinUrl() + "/" + orderNumber;
        Type responseType = String.class;
        boolean responseIsList = false;

        Request request = Request.buildRequestWithoutContent(url, method, responseType, responseIsList, token);
        String response = (String) requesterBean.sendRequest(request);
        loggerBean.logInformationMessageFromClass(response, SupplyOrderNotificationBean.class.toString());
    }

    private String getKremlinUrl() {
        if (KREMLIN_URL == null) {
            KREMLIN_URL = authenticationBean.getKremlinUrl();
        }
        return KREMLIN_URL;
    }
}
