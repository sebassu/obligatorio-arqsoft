package com.roi.planner;

import com.roi.http.Request;
import com.roi.http.RequesterBean;
import com.roi.security.AuthenticationBean;
import java.lang.reflect.Type;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class NotificationManagerBean {
    private static final String PIPELINE_URL = 
            "https://pipeline-calculator-api.herokuapp.com/pipeline-route/service";
    @EJB
    private SupplyPlanBean supplyPlanBean;
    @EJB
    private RequesterBean requesterBean;
    @EJB
    private AuthenticationBean authenticationBean;

    
    private String token;

    @PostConstruct
    public void init() {
        token = authenticationBean.getToken().toString();
    }
    
    public void created(SupplyOrderNotification notification) {
        SupplyPlan plan = createPlan(notification);
        supplyPlanBean.create(plan);
    }

    public void modified(SupplyOrderNotification notification) {
        SupplyPlan plan = supplyPlanBean.getByOrder(notification.getOrderNumber());
        boolean planIsNotYetApproved = plan.getStatus() != SupplyPlan.PlanStatus.APPROVED;
        boolean changedServicePointId = plan.getServicePointId() != notification.getServicePointId();
        if (planIsNotYetApproved && changedServicePointId) {
            SupplyPlan newPlan = createPlan(notification);
            supplyPlanBean.modify(newPlan, plan.getId());
        }
    }

    public void removed(long orderNumber) {
        SupplyPlan plan = supplyPlanBean.getByOrder(orderNumber);
        boolean planIsNotYetApproved = plan.getStatus() != SupplyPlan.PlanStatus.APPROVED;
        if (planIsNotYetApproved) {
            plan.setStatus(SupplyPlan.PlanStatus.REMOVED);
            supplyPlanBean.modify(plan, plan.getId());
        }
    }

    private SupplyPlan createPlan(SupplyOrderNotification notification) {
        String url = PIPELINE_URL + "/" + notification.getServicePointId();
        String method = "POST";
        Type responseType = NetworkFrame.class;
        boolean responseIsList = true;
        Request request = Request.buildRequestWithoutContent(url, method, responseType, responseIsList, token);
        
        ArrayList<NetworkFrame> networkFrames = (ArrayList<NetworkFrame>) requesterBean
                .sendRequest(request);
        return SupplyPlan
                .fromNotificationAndNetworkFrames(notification, networkFrames);
    }
}
