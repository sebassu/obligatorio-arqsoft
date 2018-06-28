package com.roi.planner;

import javax.ejb.Local;

@Local
public interface ISupplyPlanBean {

    void create(SupplyPlan supplyPlan);

    SupplyPlan get(long id);
    
    SupplyPlan getByOrder(long orderNumber);

    void modify(SupplyPlan supplyPlan, long id);

    void remove(long id);
}
