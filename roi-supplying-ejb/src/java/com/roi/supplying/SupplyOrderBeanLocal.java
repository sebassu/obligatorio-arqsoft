/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roi.supplying;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author alumnoFI
 */
@Local
public interface SupplyOrderBeanLocal {

    void create(SupplyOrder supplyOrder);

    List getAll();

    void remove(long orderNumber);

    void modify(long orderNumber, SupplyOrder modifiedOrder);
    
}
