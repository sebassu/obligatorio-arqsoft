package com.roi.planner;

import javax.ejb.Local;

@Local
public interface NotificationManagerBeanLocal {

    void created(SupplyOrderNotification notification);

    void modified(SupplyOrderNotification notification);

    void removed(long orderNumber);
}
