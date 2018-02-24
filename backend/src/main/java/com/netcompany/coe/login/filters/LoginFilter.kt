package com.netcompany.coe.login.filters

import com.netcompany.coe.login.UserBean
import com.netcompany.coe.login.data.LoginDatabase
import com.netcompany.coe.login.exceptions.AccessDeniedException
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.ext.Provider

private const val AUTHORIZATION_HEADER = "Authorization"

@Provider
class LoginFilter @Autowired constructor(
        private val loginDatabase: LoginDatabase,
        private val userBean: UserBean
) : ContainerRequestFilter {

    override fun filter(containerRequestContext: ContainerRequestContext) {
        val authHeader = containerRequestContext.getHeaderString(AUTHORIZATION_HEADER) ?: return
        val userDto = loginDatabase.checkToken(UUID.fromString(authHeader))
        userBean.user = userDto?.username ?: throw AccessDeniedException("Invalid token")
    }
}
