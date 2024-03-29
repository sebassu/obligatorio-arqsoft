package com.roi.supplying.persistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class SupplyOrder implements Serializable {

    private static final long serialVersionUID = 42L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderNumber;
    private long clientNumber;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date supplyStart;
    private long orderedVolume;
    private long servicePointId;
    private int billingClosingDay;

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(long clientNumber) {
        this.clientNumber = clientNumber;
    }

    public Date getSupplyStart() {
        return supplyStart;
    }

    public void setSupplyStart(Date supplyStart) {
        this.supplyStart = supplyStart;
    }

    public long getOrderedVolume() {
        return orderedVolume;
    }

    public void setOrderedVolume(long orderedVolume) {
        this.orderedVolume = orderedVolume;
    }

    public long getServicePointId() {
        return servicePointId;
    }

    public void setServicePointId(long servicePointId) {
        this.servicePointId = servicePointId;
    }

    public int getBillingClosingDay() {
        return billingClosingDay;
    }

    public void setBillingClosingDay(int billingClosingDay) {
        this.billingClosingDay = billingClosingDay;
    }
}
