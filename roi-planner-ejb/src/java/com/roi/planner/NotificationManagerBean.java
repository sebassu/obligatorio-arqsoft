package com.roi.planner;

import com.roi.http.Request;
import com.roi.http.RequesterBean;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class NotificationManagerBean implements NotificationManagerBeanLocal {

    @EJB
    private SupplyPlanBean supplyPlanBean;
    @EJB
    private RequesterBean requesterBean;

    private static final String PIPELINE_URL = "https://pipeline-calculator-api.herokuapp.com/pipeline-route/service";

    @Override
    public void created(SupplyOrderNotification notification) {
        SupplyPlan plan = createPlan(notification);
        supplyPlanBean.create(plan);
    }

    @Override
    public void modified(SupplyOrderNotification notification) {
        SupplyPlan plan = supplyPlanBean.getByOrder(notification.getOrderNumber());
        boolean planIsNotYetApproved = plan.getStatus() != SupplyPlan.PlanStatus.APPROVED;
        boolean changedServicePointId = plan.getServicePointId() != notification.getServicePointId();
        if (planIsNotYetApproved && changedServicePointId) {
            SupplyPlan newPlan = createPlan(notification);
            supplyPlanBean.modify(newPlan, plan.getId());
        }
    }

    @Override
    public void removed(long orderNumber) {
        SupplyPlan plan = supplyPlanBean.getByOrder(orderNumber);
        boolean planIsNotYetApproved = plan.getStatus() != SupplyPlan.PlanStatus.APPROVED;
        if (planIsNotYetApproved) {
            plan.setStatus(SupplyPlan.PlanStatus.REMOVED);
            supplyPlanBean.modify(plan, plan.getId());
        }
    }

    private SupplyPlan createPlan(SupplyOrderNotification notification) {
        Request request = new Request();
        request.url = PIPELINE_URL + "/" + notification.getServicePointId();
        request.method = "POST";
        request.responseType = NetworkFrame.class;
        request.responseIsList = true;
        ArrayList<NetworkFrame> networkFrames = (ArrayList<NetworkFrame>) requesterBean
                .sendRequest(request);
        return SupplyPlan
                .fromNotificationAndNetworkFrames(notification, networkFrames);
    }
}
