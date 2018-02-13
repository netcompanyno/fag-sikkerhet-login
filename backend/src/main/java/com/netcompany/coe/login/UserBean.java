package com.netcompany.coe.login;

public interface UserBean {
    default String getUser() {
        return null;
    }

    void setUser(String user);

    default boolean isTwoFactor() { return true; }

    default boolean isLoggedIn() {
        return false;
    }

    default boolean isWebContext() {
        return false;
    }
}
