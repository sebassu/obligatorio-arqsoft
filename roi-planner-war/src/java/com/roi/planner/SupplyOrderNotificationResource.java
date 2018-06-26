package com.roi.planner;

import com.google.gson.Gson;
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

@Path("supply-order")
public class SupplyOrderNotificationResource {

    @EJB
    private NotificationManagerBean notificationManagerBean;

    @Context
    private UriInfo context;

    private final Gson gson;

    public SupplyOrderNotificationResource() {
        this.gson = new Gson();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createdSupplyOrder(String content) {
        SupplyOrderNotification notification = gson.fromJson(content,
                SupplyOrderNotification.class);
        notificationManagerBean.created(notification);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void modifiedSupplyOrder(@PathParam("id") Long id, String body) {
        SupplyOrderNotification notification = gson.fromJson(body,
                SupplyOrderNotification.class);
        notificationManagerBean.modified(notification);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void removedSupplyOrder(@PathParam("id") Long id) {
        notificationManagerBean.removed(id);
    }
}
