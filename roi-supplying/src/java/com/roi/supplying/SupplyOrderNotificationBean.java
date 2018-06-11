package com.roi.supplying;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import roi.utilities.SupplyOrderNotification;
import roi.utilities.SupplyOrderNotification.NotificationType;

@Stateless
@LocalBean
public class SupplyOrderNotificationBean {

    public void notify(SupplyOrder supplyOrder, NotificationType type) {
        long orderNumber = supplyOrder.getOrderNumber();
        long servicePointId = supplyOrder.getServicePointId();
        
        SupplyOrderNotification notification = new 
            SupplyOrderNotification(orderNumber, servicePointId, type);
        
    }
    
}


