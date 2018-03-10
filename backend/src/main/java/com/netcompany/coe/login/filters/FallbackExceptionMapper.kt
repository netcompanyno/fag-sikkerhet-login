package com.netcompany.coe.login.filters

import org.slf4j.LoggerFactory
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class FallbackExceptionMapper : ExceptionMapper<Exception> {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    override fun toResponse(e: Exception): Response {
        log.error(e.message, e)
        return Response.status(INTERNAL_SERVER_ERROR).build()
    }
}
