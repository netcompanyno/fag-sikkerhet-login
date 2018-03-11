package com.netcompany.coe.login.data

import com.netcompany.coe.login.dto.UserSettingsDto
import com.netcompany.coe.login.enums.SecurityStep.TOTP
import org.springframework.stereotype.Repository

private val DEFAULT = UserSettingsDto(securitySteps = emptySet())

@Repository
class UserSettingsDatabase {

    private val usernameToSettings = mapOf(
            "admin" to UserSettingsDto(securitySteps = setOf(TOTP))
    )


    fun findUserSettings(username: String): UserSettingsDto {
        return usernameToSettings[username.toLowerCase()] ?: DEFAULT
    }
}
