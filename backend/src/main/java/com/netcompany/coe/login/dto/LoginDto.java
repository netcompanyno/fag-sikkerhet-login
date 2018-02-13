package com.netcompany.coe.login.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class LoginDto {

    String username;
    String password;

    @JsonCreator
    public LoginDto(@JsonProperty("username") final String username,
                    @JsonProperty("password") final String password) {
        this.username = username;
        this.password = password;
    }
}
