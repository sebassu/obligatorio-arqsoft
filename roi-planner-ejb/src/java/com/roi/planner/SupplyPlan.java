package com.roi.planner;

import java.util.ArrayList;


public class SupplyPlan {
    
    private long orderNumber;
    private long servicePointId;
    private ArrayList<NetworkFrame> networkFrames;

    public SupplyPlan(){
        networkFrames = new ArrayList<>();
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
