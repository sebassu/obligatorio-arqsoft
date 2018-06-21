package com.roi.planner;

import javax.ejb.Stateless;

@Stateless
public class NotificationManagerBean implements NotificationManagerBeanLocal {

    @Override
    public void created(SupplyOrderNotification notification) {
        //TODO persist)? (with provided id) & create plan
    
    }
    
    @Override
    public void modified(SupplyOrderNotification notification) {
    }
    
    @Override
    public void removed() {
    }
}
