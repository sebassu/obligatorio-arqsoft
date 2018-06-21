package com.roi.supplying;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import roi.utilities.SupplyOrderNotification;

@Stateless
@LocalBean
public class SupplyOrderNotificationBean {
    //TODO remove maybe
    private SupplyOrderNotification createNotification(SupplyOrder supplyOrder) {
        long orderNumber = supplyOrder.getOrderNumber();
        long servicePointId = supplyOrder.getServicePointId();
        
        return new SupplyOrderNotification(orderNumber, servicePointId);
    }
    
    public void notifyCreation(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        
        //TODO send notification
    }
}


