package com.netcompany.coe.login;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class UserBeanImpl implements UserBean {
    @Context
    private HttpServletRequest servletRequest;
    private String user = null;

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public boolean isWebContext() {
        return true;
    }
}