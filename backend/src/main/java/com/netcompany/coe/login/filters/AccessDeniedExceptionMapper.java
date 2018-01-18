package com.netcompany.coe.login.filters;

import com.netcompany.coe.login.exceptions.AccessDeniedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {
    @Override
    public Response toResponse(final AccessDeniedException e) {
        return Response.status(403).build();
    }
}
