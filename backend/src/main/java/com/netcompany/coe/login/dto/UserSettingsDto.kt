package com.netcompany.coe.login.dto

import com.netcompany.coe.login.enums.SecurityStep

data class UserSettingsDto(
        val securitySteps: Set<SecurityStep>
)
