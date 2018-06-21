/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roi.planner;

import javax.ejb.Local;

/**
 *
 * @author Pablo
 */
@Local
public interface NotificationManagerBeanLocal {

    void created(SupplyOrderNotification notification);

    void modified(SupplyOrderNotification notification);

    void removed();
}
