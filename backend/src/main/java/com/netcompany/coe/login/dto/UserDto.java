package com.netcompany.coe.login.dto;

public class UserDto {
    private final String username;
    private final String passwordHash;

    public UserDto(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
