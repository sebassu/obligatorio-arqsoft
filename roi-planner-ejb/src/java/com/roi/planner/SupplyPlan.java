package com.roi.planner;

import java.util.ArrayList;


public class SupplyPlan {
    
    private long orderNumber;
    private long servicePointId;
    private ArrayList<NetworkFrame> networkFrames;

    private SupplyPlan(){
        networkFrames = new ArrayList<>();
    }
    
    public static SupplyPlan fromNotificationAndNetworkFrames
        (SupplyOrderNotification notification, ArrayList<NetworkFrame> networkFrames){
            SupplyPlan plan = new SupplyPlan();
            plan.setOrderNumber(notification.getOrderNumber());
            plan.setServicePointId(notification.getServicePointId());
            plan.networkFrames = networkFrames;
            return plan;
    }
    
    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getServicePointId() {
        return servicePointId;
    }

    public void setServicePointId(long servicePointId) {
        this.servicePointId = servicePointId;
    }

    public ArrayList<NetworkFrame> getNetworkFrames() {
        return networkFrames;
    }

}
