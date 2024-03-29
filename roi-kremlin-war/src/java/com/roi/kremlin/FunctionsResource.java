package com.roi.kremlin;

import com.roi.kremlin.authorization.AuthorizationBean;
import com.roi.kremlin.redirector.SenderBean;
import com.roi.kremlin.services.ServicesBean;
import com.roi.kremlin.validation.ValidatorBean;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("functions")
public class FunctionsResource {
    
    @EJB
    private AuthorizationBean authorizationBean;
    
    @EJB
    private SenderBean senderBean;

    @EJB
    private ValidatorBean validatorBean;
    
    @EJB
    private ServicesBean servicesBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecification() {
        return Response.ok(servicesBean.getProducersSpecs()).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{appName}/{function}")
    public Response call(@PathParam("appName")String appName, 
            @PathParam("function")String function, String content, @Context HttpHeaders headers) {

        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        if (authHeaders.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String token = authHeaders.get(0);
        boolean isAuthorized = authorizationBean.isAuthorized(appName, function, token);
        if (!isAuthorized) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        boolean isValidCall = validatorBean.isValid(appName, function, content);
        if (!isValidCall) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        senderBean.send(appName, function, content);

        return Response.ok().build();
    }
}
