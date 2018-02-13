package com.netcompany.coe.login.rest;

import com.netcompany.coe.login.UserBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class UserRestService {

    private final UserBean userBean;

    @GET
    public Response userInfo() {
        final String username = userBean.getUser();
        if (username == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(username).build();
    }
}
