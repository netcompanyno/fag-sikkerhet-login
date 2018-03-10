package com.netcompany.coe.login.rest

import com.netcompany.coe.login.UserBean
import com.netcompany.coe.login.exceptions.UnauthorizedException
import org.springframework.stereotype.Service
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Service
@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserRestService(
        private val userBean: UserBean
) {

    @GET
    fun userInfo(): Response {
        return userBean.username
                ?.let { Response.ok(it).build() }
                ?: throw UnauthorizedException()
    }
}
