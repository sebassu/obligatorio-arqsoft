package com.roi.planner;

import com.google.gson.Gson;
import com.roi.planner.approval.IPlanApproverBean;
import com.roi.planner.INotificationManagerBean;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

public class SupplyOrderNotificationResource {

    @EJB
    private IPlanApproverBean planApproverBean;
    
    @EJB
    private INotificationManagerBean notificationManagerBean;

    @Context
    private UriInfo context;

    private final Gson gson;

    public SupplyOrderNotificationResource() {
        this.gson = new Gson();
    }

    @POST
    @Path("supply-order")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createdSupplyOrder(String content) {
        SupplyOrderNotification notification = gson.fromJson(content,
                SupplyOrderNotification.class);
        notificationManagerBean.created(notification);
    }

    @PUT
    @Path("supply-order/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void modifiedSupplyOrder(@PathParam("id") Long id, String body) {
        SupplyOrderNotification notification = gson.fromJson(body,
                SupplyOrderNotification.class);
        notificationManagerBean.modified(notification);
    }

    @DELETE
    @Path("supply-order/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void removedSupplyOrder(@PathParam("id") Long id) {
        notificationManagerBean.removed(id);
    }
    
    @POST
    @Path("supply-plan/{id}")
    public void approveSupplyPlan(@PathParam("id") Long id) {
        planApproverBean.approve(id);
    }
}
