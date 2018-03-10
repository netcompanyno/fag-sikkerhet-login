package com.netcompany.coe.login.filters

import com.netcompany.coe.login.UserBean
import com.netcompany.coe.login.exceptions.UnauthorizedException
import com.netcompany.coe.login.service.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.ext.Provider

private const val AUTHORIZATION_HEADER = "Authorization"

@Provider
class AuthenticationFilter @Autowired constructor(
        private val authenticationService: AuthenticationService,
        private val userBean: UserBean
) : ContainerRequestFilter {

    override fun filter(containerRequestContext: ContainerRequestContext) {
        containerRequestContext.getHeaderString(AUTHORIZATION_HEADER)?.let(this::authenticate)
    }

    private fun authenticate(authHeader: String) {
        try {
            authenticationService.verifySecurityStepsCompleted(authHeader)
            userBean.username = authenticationService.findUsername(authHeader)
        } catch (e: Exception) {
            throw UnauthorizedException(e.message)
        }
    }

}
