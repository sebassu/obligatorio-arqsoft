package com.roi.kremlin;

import com.google.gson.Gson;
import com.roi.kremlin.models.ValidatorBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ejb.EJB;
import javax.ws.rs.core.Response;

@Path("functions")
public class FunctionsResource {

    @EJB
    ValidatorBean validatorBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{appName}/{function}")
    public Response call(String appName, String function, String content) {
        //TODO validate with authBean its auth and its allowed to call appName
        boolean isValidCall = validatorBean.isValidCall(appName, function, content);
        if (isValidCall) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
