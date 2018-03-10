package com.netcompany.coe.login.rest

import com.netcompany.coe.login.dto.LoginDto
import com.netcompany.coe.login.dto.SecurityStepLoginDto
import com.netcompany.coe.login.service.LoginService
import org.springframework.stereotype.Service
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Service
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class LoginRestService(
        private val loginService: LoginService
) {

    @POST
    fun login(loginDto: LoginDto): Response {
        return Response.ok(loginService.getAuthenticationToken(loginDto)).build()
    }

    @POST
    @Path("security-step")
    fun securityStep(securityStepLoginDto: SecurityStepLoginDto): Response {
        return Response.ok(loginService.getAuthenticationToken(securityStepLoginDto)).build()
    }
}
