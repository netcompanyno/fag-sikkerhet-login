package com.netcompany.coe.login.filters

import com.netcompany.coe.login.exceptions.AuthenticationException
import org.slf4j.LoggerFactory
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status.FORBIDDEN
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class AuthenticationExceptionMapper : ExceptionMapper<AuthenticationException> {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    override fun toResponse(e: AuthenticationException): Response {
        log.warn(e.message, e)
        return Response.status(FORBIDDEN).build()
    }
}
