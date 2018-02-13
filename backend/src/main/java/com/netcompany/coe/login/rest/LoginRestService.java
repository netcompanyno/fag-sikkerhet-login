package com.netcompany.coe.login.rest;

import com.netcompany.coe.login.dto.LoginDto;
import com.netcompany.coe.login.service.LoginService;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginRestService {
    private final LoginService loginService;

    public LoginRestService(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    public Response login(final LoginDto loginDto) {
        final String userToken = loginService.getUserToken(loginDto);

        if (userToken == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(userToken).build();
    }
}
