package com.roi.planner;

import com.roi.http.Requester;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class PlanApproverBean {

    private static final String KREMLIN_URL = "https://";

    @EJB
    SupplyPlanBean supplyPlanBean;

    public void approve(long planId) {
        SupplyPlan plan = supplyPlanBean.get(planId);
        plan.setStatus(SupplyPlan.PlanStatus.APPROVED);
        supplyPlanBean.modify(plan, planId);
        
        Requester.sendRequest(KREMLIN_URL, "POST", String.class, false, plan, SupplyPlan.class);
    }
}
