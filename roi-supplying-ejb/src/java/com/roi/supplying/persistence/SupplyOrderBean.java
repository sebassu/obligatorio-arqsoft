package com.roi.supplying.persistence;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
@LocalBean
public class SupplyOrderBean implements ISupplyOrderBean {

    @PersistenceContext(unitName = "SupplyingPU")
    private EntityManager entityManager;

    @Override
    public void create(SupplyOrder supplyOrder) {
        entityManager.persist(supplyOrder);
        entityManager.flush();
    }

    @Override
    public List<SupplyOrder> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SupplyOrder> cq = cb.createQuery(SupplyOrder.class);
        Root<SupplyOrder> rootEntry = cq.from(SupplyOrder.class);
        CriteriaQuery<SupplyOrder> all = cq.select(rootEntry);
        TypedQuery<SupplyOrder> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public void modify(long orderNumber, SupplyOrder modifiedOrder) {
        SupplyOrder supplyOrder = entityManager.find(SupplyOrder.class, orderNumber);
        supplyOrder.setBillingClosingDay(modifiedOrder.getBillingClosingDay());
        supplyOrder.setClientNumber(modifiedOrder.getClientNumber());
        supplyOrder.setOrderedVolume(modifiedOrder.getOrderedVolume());
        supplyOrder.setServicePointId(modifiedOrder.getServicePointId());
        supplyOrder.setSupplyStart(modifiedOrder.getSupplyStart());
        entityManager.merge(supplyOrder);
        entityManager.flush();
    }

    @Override
    public void remove(long orderNumber) {
        SupplyOrder supplyOrder = entityManager.find(SupplyOrder.class, orderNumber);
        entityManager.remove(supplyOrder);
        entityManager.flush();
    }
}
