package com.netcompany.coe.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class UserDto {

    String username;
    String passwordHash;

}
