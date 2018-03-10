package com.netcompany.coe.login.service

import com.netcompany.coe.login.data.ThrottlingDatabase
import com.netcompany.coe.login.data.UserDatabase
import com.netcompany.coe.login.data.UserSettingsDatabase
import com.netcompany.coe.login.dto.LoginDto
import com.netcompany.coe.login.dto.SecurityStepLoginDto
import com.netcompany.coe.login.enums.SecurityStep.TWO_FACTOR_AUTH_TOTP
import com.netcompany.coe.login.exceptions.AuthenticationException
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginService(
        private val userDatabase: UserDatabase,
        private val userSettingsDatabase: UserSettingsDatabase,
        private val authenticationService: AuthenticationService,
        private val totpService: TotpService,
        private val passwordEncoder: PasswordEncoder,
        private val throttlingDatabase: ThrottlingDatabase
) {

    private val log = LoggerFactory.getLogger(this.javaClass.name)
    private val dummyPassword: String = passwordEncoder.encode("x")

    fun getAuthenticationToken(loginDto: LoginDto): String {
        val user = userDatabase.findUser(loginDto.username) ?: run {
            fakePasswordCheck(loginDto)
            throw AuthenticationException("User not found [${loginDto.username}]")
        }

        if (throttlingDatabase.isIllegalLoginAttempt(user.username)) {
            fakePasswordCheck(loginDto)
            throttlingDatabase.recordLoginAttempt(user.username)
            throw AuthenticationException("Throttling login attempt for user [${user.username}]")
        }

        if (passwordEncoder.matches(loginDto.password, user.passwordHash)) {
            log.info("Successful password login for user [{}]", user.username)
            throttlingDatabase.successfulLogin(user.username)
            val userSettings = userSettingsDatabase.findUserSettings(user.username)
            return authenticationService.createAuthenticationToken(user, userSettings)
        }

        throttlingDatabase.recordLoginAttempt(user.username)
        throw AuthenticationException("Failed login attempt for user [${user.username}]")
    }

    fun getAuthenticationToken(securityStepLoginDto: SecurityStepLoginDto): String {
        return when (securityStepLoginDto.securityStep) {
            TWO_FACTOR_AUTH_TOTP -> totpService.createAuthenticationToken(securityStepLoginDto)
        }
    }

    private fun fakePasswordCheck(loginDto: LoginDto): Boolean =
            passwordEncoder.matches(loginDto.password, dummyPassword)

}
