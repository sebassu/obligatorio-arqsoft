package com.roi.planner;

public interface INotificationManagerBean {
    void created(SupplyOrderNotification notification);
    void modified(SupplyOrderNotification notification);
    void removed(long orderNumber);
}
