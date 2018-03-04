package com.netcompany.coe.login.filters

import com.netcompany.coe.login.UserBean
import com.netcompany.coe.login.exceptions.AccessDeniedException
import com.netcompany.coe.login.service.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.ext.Provider

private const val AUTHORIZATION_HEADER = "Authorization"

@Provider
class LoginFilter @Autowired constructor(
        private val authenticationService: AuthenticationService,
        private val userBean: UserBean
) : ContainerRequestFilter {

    override fun filter(containerRequestContext: ContainerRequestContext) {
        val authHeader = containerRequestContext.getHeaderString(AUTHORIZATION_HEADER) ?: return
        userBean.user = authenticationService.findUserName(authHeader) ?: throw AccessDeniedException("Invalid token")
    }
}
