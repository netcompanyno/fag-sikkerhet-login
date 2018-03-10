package com.netcompany.coe.login.filters

import com.netcompany.coe.login.exceptions.UnauthorizedException
import org.slf4j.LoggerFactory
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status.UNAUTHORIZED
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class UnauthorizedExceptionMapper : ExceptionMapper<UnauthorizedException> {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    override fun toResponse(e: UnauthorizedException): Response {
        log.warn(e.message, e)
        return Response.status(UNAUTHORIZED).build()
    }
}
