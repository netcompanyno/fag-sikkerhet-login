package com.netcompany.coe.login.filters

import com.netcompany.coe.login.exceptions.AccessDeniedException

import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class AccessDeniedExceptionMapper : ExceptionMapper<AccessDeniedException> {

    override fun toResponse(e: AccessDeniedException): Response {
        return Response.status(403).build()
    }
}
