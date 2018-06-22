package com.roi.planner;

import com.roi.http.Requester;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class NotificationManagerBean implements NotificationManagerBeanLocal {

    @EJB
    private SupplyPlanBean supplyPlanBean;
    
    private static final String PIPELINE_URL = "https://pipeline-calculator-api.herokuapp.com/pipeline-route/service";
     
    @Override
    public void created(SupplyOrderNotification notification) {
        SupplyPlan plan = createPlan(notification);
        supplyPlanBean.create(plan);
        //TODO Send to Goliath
    }
    
    @Override
    public void modified(SupplyOrderNotification notification) {
        SupplyPlan plan = supplyPlanBean.getByOrder(notification.getOrderNumber());
        boolean changedServicePointId = plan.getServicePointId() != notification.getServicePointId();
        if(changedServicePointId){
            SupplyPlan newPlan = createPlan(notification);
            supplyPlanBean.modify(newPlan, plan.getId());
        }
    }
    
    @Override
    public void removed(long orderNumber) {
        SupplyPlan plan = supplyPlanBean.getByOrder(orderNumber);
        plan.setRemoved(true);
        supplyPlanBean.modify(plan, plan.getId());
    }
    
    private SupplyPlan createPlan(SupplyOrderNotification notification){
        String url = PIPELINE_URL + "/" + notification.getServicePointId();
        ArrayList<NetworkFrame> networkFrames = (ArrayList<NetworkFrame>)Requester.
                sendRequest(url, "POST", NetworkFrame.class, true);
        return SupplyPlan
                .fromNotificationAndNetworkFrames(notification, networkFrames);
    }
}
