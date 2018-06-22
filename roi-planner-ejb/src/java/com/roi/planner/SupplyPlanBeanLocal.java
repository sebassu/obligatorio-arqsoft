package com.roi.planner;

import javax.ejb.Local;

@Local
public interface SupplyPlanBeanLocal {

    void create(SupplyPlan supplyPlan);

    SupplyPlan get(long id);

    void modify(SupplyPlan supplyPlan, long id);

    void remove(long id);
}
