package com.roi.planner;

import com.roi.http.Request;
import com.roi.http.RequesterBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class PlanApproverBean {

    private static final String KREMLIN_URL = "https://";

    @EJB
    SupplyPlanBean supplyPlanBean;
    @EJB
    RequesterBean requesterBean;

    public void approve(long planId) {
        SupplyPlan plan = supplyPlanBean.get(planId);
        plan.setStatus(SupplyPlan.PlanStatus.APPROVED);
        supplyPlanBean.modify(plan, planId);
        
        Request request = new Request();
        request.url = KREMLIN_URL;
        request.method = "POST";
        request.responseType = String.class;
        request.responseIsList = false;
        request.content = plan;
        request.contentType = SupplyPlan.class;
        requesterBean.sendRequest(request);
    }
}
