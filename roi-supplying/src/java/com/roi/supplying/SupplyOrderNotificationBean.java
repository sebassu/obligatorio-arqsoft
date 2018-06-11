package com.roi.supplying;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import roi.utilities.SupplyOrderNotification;

@Stateless
@LocalBean
public class SupplyOrderNotificationBean {

    public void notifyCreation(SupplyOrder supplyOrder) {
        long orderNumber = supplyOrder.getOrderNumber();
        long servicePointId = supplyOrder.getServicePointId();
        
        SupplyOrderNotification notification = new SupplyOrderNotification(orderNumber, servicePointId, SupplyOrderNotification.NotificationType.CREATED);
        
    }
}


