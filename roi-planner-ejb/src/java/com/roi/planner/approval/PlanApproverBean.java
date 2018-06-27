package com.roi.planner.approval;

import com.roi.http.Request;
import com.roi.http.RequesterBean;
import com.roi.planner.SupplyPlan;
import com.roi.planner.SupplyPlanBean;
import com.roi.security.AuthenticationBean;
import java.lang.reflect.Type;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class PlanApproverBean {

    private String KREMLIN_URL;

    @EJB
    SupplyPlanBean supplyPlanBean;
    @EJB
    RequesterBean requesterBean;
    @EJB
    AuthenticationBean authenticationBean;

    private String token;

    @PostConstruct
    public void init() {
        token = authenticationBean.getToken().toString();
    }

    public void approve(long planId) {
        SupplyPlan plan = supplyPlanBean.get(planId);
        plan.setStatus(SupplyPlan.PlanStatus.APPROVED);
        supplyPlanBean.modify(plan, planId);

        String url = getKremlinUrl();
        String method = "POST";
        Type responseType = String.class;
        Boolean responseIsList = false;
        Type contentType = SupplyPlan.class;

        Request request = Request.buildRequestWithContent(url, method, responseType,
                responseIsList, token, plan, contentType);
        requesterBean.sendRequest(request);
    }

    private String getKremlinUrl() {
        if (KREMLIN_URL == null) {
            KREMLIN_URL = authenticationBean.getKremlinUrl();
        }
        return KREMLIN_URL;
    }
}
