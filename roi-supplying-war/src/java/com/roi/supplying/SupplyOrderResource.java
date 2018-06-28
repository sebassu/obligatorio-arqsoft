package com.roi.supplying;

import com.roi.supplying.persistence.ISupplyOrderBean;
import com.roi.supplying.persistence.SupplyOrder;
import com.google.gson.Gson;
import com.roi.supplying.notification.ISupplyOrderNotificationBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("supply-order")
public class SupplyOrderResource {

    @EJB
    private ISupplyOrderBean supplyOrderBean;
    
    @EJB
    private ISupplyOrderNotificationBean supplyOrderNotificationBean;

    @Context
    private UriInfo context;
    private final Gson gson;

    public SupplyOrderResource() {
        this.gson = new Gson();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupplyOrders() {
        List<SupplyOrder> supplyOrders = supplyOrderBean.getAll();
        return Response.ok()
                    .entity(supplyOrders)
                    .build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSupplyOrder(String content) {
        Response response;
        SupplyOrder supplyOrder = gson.fromJson(content, SupplyOrder.class);
        if (supplyOrder == null) {
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid Supply Order provided")
                    .build();
        } else {
            supplyOrderBean.create(supplyOrder);
            supplyOrderNotificationBean.notifyCreation(supplyOrder);
            response = Response.ok()
                    .entity(supplyOrder)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return response;
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifySupplyOrder(@PathParam("id") Long id, String body) {
        Response response;
        SupplyOrder supplyOrder = gson.fromJson(body, SupplyOrder.class);
        if (supplyOrder == null) {
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid Supply Order provided")
                    .build();
        } else {
            
            supplyOrderBean.modify(id, supplyOrder);
            supplyOrderNotificationBean.notifyModification(supplyOrder);
            response = Response.ok()
                    .entity(supplyOrder)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return response;
    }

    @DELETE
    @Path("{id}")
    public Response removeSupplyOrder(@PathParam("id") Long id) {
        Response response;
        supplyOrderBean.remove(id);
        supplyOrderNotificationBean.notifyRemoval(id);
        response = Response.status(Response.Status.OK).build();
        return response;
    }
    

}
