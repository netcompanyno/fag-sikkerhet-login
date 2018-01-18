package com.netcompany.coe.login.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDto {
    private final String username;
    private final String password;

    @JsonCreator
    public LoginDto(@JsonProperty("username") final String username,
                    @JsonProperty("password") final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
