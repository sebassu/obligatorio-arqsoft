package com.roi.supplying.notification;

import com.roi.supplying.SupplyOrder;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class SupplyOrderNotificationBean {

    private static final String KREMLIN_URL = "Kremlin URL";

    private SupplyOrderNotification createNotification(SupplyOrder supplyOrder) {
        long orderNumber = supplyOrder.getOrderNumber();
        long servicePointId = supplyOrder.getServicePointId();

        return new SupplyOrderNotification(orderNumber, servicePointId);
    }

    public void notifyCreation(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        String url = KREMLIN_URL;
        String s = Requester.sendRequest(url, "POST", notification, SupplyOrderNotification.class);
    }

    public void notifyModification(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        String url = KREMLIN_URL + "/" + supplyOrder.getOrderNumber();
        String s = Requester.sendRequest(url, "PUT", notification, SupplyOrderNotification.class);
    }

    public void notifyRemoval(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        String url = KREMLIN_URL + "/" + supplyOrder.getOrderNumber();
        Requester.sendRequest(url, "DELETE");
    }
    
}
