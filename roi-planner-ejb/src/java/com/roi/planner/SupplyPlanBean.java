package com.roi.planner;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SupplyPlan> cq = cb.createQuery(SupplyPlan.class);
        Root<SupplyPlan> rootEntry = cq.from(SupplyPlan.class);
        Predicate cond;
        cond = cb.equal(rootEntry.get("orderNumber"), orderNumber);
        TypedQuery<SupplyPlan> query = entityManager.createQuery(cq.where(cond));
        return query.getSingleResult();
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
