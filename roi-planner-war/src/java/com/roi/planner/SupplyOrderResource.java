package com.roi.planner;

import com.google.gson.Gson;
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
public class SupplyOrderResource {

    @Context
    private UriInfo context;
    
    private final Gson gson;

    public SupplyOrderResource() {
        this.gson = new Gson();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postSupplyOrder(String content) {
        SupplyOrderNotification notification = gson.fromJson(content, SupplyOrderNotification.class);
        //TODO persist)? (with provided id) & create plan
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putSupplyOrder(@PathParam("id") Long id, String body) {
        //TODO modify)? & modify  plan
    }
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteSupplyOrder(@PathParam("id") Long id) {
        throw new UnsupportedOperationException();
    }
    
}