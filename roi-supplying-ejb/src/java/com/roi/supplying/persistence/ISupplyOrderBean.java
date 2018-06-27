package com.roi.supplying.persistence;

import java.util.List;
import javax.ejb.Local;

@Local
public interface ISupplyOrderBean {

    void create(SupplyOrder supplyOrder);

    List getAll();

    void remove(long orderNumber);

    void modify(long orderNumber, SupplyOrder modifiedOrder);
}
