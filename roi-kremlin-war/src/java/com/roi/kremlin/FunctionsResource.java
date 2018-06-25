package com.roi.kremlin;

import com.roi.kremlin.models.AuthorizationBean;
import com.roi.kremlin.models.ValidatorBean;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("functions")
public class FunctionsResource {

    @EJB
    AuthorizationBean authorizationBean;

    @EJB
    ValidatorBean validatorBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{appName}/{function}")
    public Response call(String appName, String function, String content, @Context HttpHeaders headers) {

        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        if (authHeaders.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String token = authHeaders.get(0);
        boolean isAuthorized = authorizationBean.isAuthorized(appName, function, token);
        if (!isAuthorized) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        boolean isValidCall = validatorBean.isValidCall(appName, function, content);
        if (!isValidCall) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }
}
