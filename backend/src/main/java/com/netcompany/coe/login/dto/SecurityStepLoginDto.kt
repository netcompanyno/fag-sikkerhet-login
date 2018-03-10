package com.netcompany.coe.login.dto

import com.netcompany.coe.login.enums.SecurityStep

data class SecurityStepLoginDto(
        val authenticationToken: String,
        val securityStep: SecurityStep,
        val password: String
)
