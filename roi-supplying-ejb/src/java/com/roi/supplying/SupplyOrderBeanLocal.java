package com.roi.supplying;

import java.util.List;
import javax.ejb.Local;

@Local
public interface SupplyOrderBeanLocal {

    void create(SupplyOrder supplyOrder);

    List getAll();

    void remove(long orderNumber);

    void modify(long orderNumber, SupplyOrder modifiedOrder);
}
