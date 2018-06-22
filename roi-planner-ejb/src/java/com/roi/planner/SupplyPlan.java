package com.roi.planner;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SupplyPlan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private long orderNumber;
    private long servicePointId;
    private ArrayList<NetworkFrame> networkFrames;
    
    private boolean removed = false;

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public SupplyPlan() {
    }

    public static SupplyPlan fromNotificationAndNetworkFrames(SupplyOrderNotification notification, ArrayList<NetworkFrame> networkFrames) {
        SupplyPlan plan = new SupplyPlan();
        plan.setOrderNumber(notification.getOrderNumber());
        plan.setServicePointId(notification.getServicePointId());
        plan.networkFrames = networkFrames;
        return plan;
    }
    
    public long getId() {
        return id;
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
