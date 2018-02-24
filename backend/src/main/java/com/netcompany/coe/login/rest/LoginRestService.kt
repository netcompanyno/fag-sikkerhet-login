package com.netcompany.coe.login.rest

import com.netcompany.coe.login.dto.LoginDto
import com.netcompany.coe.login.service.LoginService
import org.springframework.stereotype.Service
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status.FORBIDDEN

@Service
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class LoginRestService(
        private val loginService: LoginService
) {

    @POST
    fun login(loginDto: LoginDto): Response {
        val userToken = loginService.getUserToken(loginDto) ?: return Response.status(FORBIDDEN).build()
        return Response.ok(userToken).build()
    }
}
