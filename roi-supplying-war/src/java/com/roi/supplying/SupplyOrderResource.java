package com.roi.supplying;

import com.google.gson.Gson;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("supply-order")
public class SupplyOrderResource {
    
    @EJB
    private SupplyOrderBean supplyOrderBean;
    
    @Context
    private UriInfo context;
    private final Gson gson;

    public SupplyOrderResource() {
        this.gson = new Gson();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createSupplyOrder(String content) {
        SupplyOrder supplyOrder = gson.fromJson(content, SupplyOrder.class);
        supplyOrderBean.create(supplyOrder);
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void modifySupplyOrder(@PathParam("id") Long id, String body) {
        SupplyOrder supplyOrder = gson.fromJson(body, SupplyOrder.class);
        supplyOrderBean.modify(id, supplyOrder);
    }
    
    @DELETE
    @Path("{id}")
    public void removeSupplyOrder(@PathParam("id") Long id) {
        supplyOrderBean.remove(id);
    }
    
}