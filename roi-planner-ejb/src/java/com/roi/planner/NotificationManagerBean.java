package com.roi.planner;

import javax.ejb.Stateless;

@Stateless
public class NotificationManagerBean implements NotificationManagerBeanLocal {

    @Override
    public void created(SupplyOrderNotification notification) {
        // TODO hit pipelinecalc and create Plan
        // Send to Goliath
    }
    
    @Override
    public void modified(SupplyOrderNotification notification) {
        // TODO if servicePointId changed hit pipelinecalc
        // Then modify Plan
    }
    
    @Override
    public void removed() {
        // TODO logically remove
    }
}
