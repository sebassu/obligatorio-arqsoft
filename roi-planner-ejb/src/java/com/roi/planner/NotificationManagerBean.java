package com.roi.planner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import javax.ejb.Stateless;

@Stateless
public class NotificationManagerBean implements NotificationManagerBeanLocal {

    private static final String PIPELINE_URL = "https://pipeline-calculator-api.herokuapp.com/pipeline-route/service";
     
    @Override
    public void created(SupplyOrderNotification notification) {
        String url = PIPELINE_URL + "/" + notification.getServicePointId();
        ArrayList<NetworkFrame> networkFrames = (ArrayList<NetworkFrame>)Requester.
                sendRequest(url, "POST", NetworkFrame.class, true);
        SupplyPlan plan = SupplyPlan
                .fromNotificationAndNetworkFrames(notification, networkFrames);
        //TODO Send to Goliath
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
