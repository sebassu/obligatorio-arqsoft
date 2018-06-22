package com.roi.planner;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class SupplyPlanBean implements SupplyPlanBeanLocal {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(SupplyPlan supplyPlan) {
        entityManager.persist(supplyPlan);
        entityManager.flush();
    }

    @Override
    public SupplyPlan get(long id) {
        return entityManager.find(SupplyPlan.class, id);
    }
    
    @Override
    public SupplyPlan getByOrder(long orderNumber) {
        // TODO query to ferch related plan
        return null;
    }

    @Override
    public void modify(SupplyPlan supplyPlan, long id) {
        SupplyPlan existingSupplyPlan = entityManager.find(SupplyPlan.class, id);
        existingSupplyPlan.setServicePointId(existingSupplyPlan.getServicePointId());
        entityManager.merge(existingSupplyPlan);
        entityManager.flush();
    }

    @Override
    public void remove(long id) {
        entityManager.remove(id);
    }

}
