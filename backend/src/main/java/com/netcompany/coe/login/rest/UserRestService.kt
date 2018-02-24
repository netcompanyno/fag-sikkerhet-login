package com.netcompany.coe.login.rest

import com.netcompany.coe.login.UserBean
import org.springframework.stereotype.Service
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status.FORBIDDEN

@Service
@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserRestService(
        private val userBean: UserBean
) {

    @GET
    fun userInfo(): Response {
        val username = userBean.user ?: return Response.status(FORBIDDEN).build()
        return Response.ok(username).build()
    }
}
