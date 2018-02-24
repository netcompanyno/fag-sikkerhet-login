package com.netcompany.coe.login

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
class UserBeanImpl(
        @Context private val servletRequest: HttpServletRequest
) : UserBean {

    override var user: String? = null

    override fun isLoggedIn(): Boolean {
        return user != null
    }

    override fun isWebContext(): Boolean {
        return true
    }
}