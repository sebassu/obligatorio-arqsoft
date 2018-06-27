package com.roi.supplying.notification;

import com.roi.supplying.persistence.SupplyOrder;

public interface ISupplyOrderNotificationBean {
    void notifyCreation(SupplyOrder supplyOrder);
    void notifyModification(SupplyOrder supplyOrder);
    void notifyRemoval(long id);
}
