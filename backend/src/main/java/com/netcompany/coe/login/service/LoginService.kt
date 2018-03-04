package com.netcompany.coe.login.service

import com.netcompany.coe.login.data.ThrottlingDatabase
import com.netcompany.coe.login.data.UserDatabase
import com.netcompany.coe.login.dto.LoginDto
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LoginService(
        private val userDatabase: UserDatabase,
        private val authenticationService: AuthenticationService,
        private val passwordEncoder: PasswordEncoder,
        private val throttlingDatabase: ThrottlingDatabase
) {

    private val log = LoggerFactory.getLogger(this.javaClass.name)
    private val dummyPassword: String = passwordEncoder.encode("x")

    fun getUserToken(loginDto: LoginDto): String? {
        val user = userDatabase.findUser(loginDto.username) ?: run {
            fakePasswordCheck(loginDto)
            log.info("User not found [{}]", loginDto.username)
            return null
        }

        if (throttlingDatabase.isIllegalLoginAttempt(user.username)) {
            fakePasswordCheck(loginDto)
            throttlingDatabase.recordLoginAttempt(user.username)
            log.debug("({}) Throttling login attempt for user [{}]", LocalDateTime.now(), user.username)
            return null
        }

        if (passwordEncoder.matches(loginDto.password, user.passwordHash)) {
            throttlingDatabase.successfulLogin(user.username)
            log.info("Successful login for user [{}]", user.username)
            return authenticationService.createAuthenticationToken(user)
        }

        throttlingDatabase.recordLoginAttempt(user.username)
        log.warn("Failed login attempt for user [{}]", user.username)
        return null
    }

    private fun fakePasswordCheck(loginDto: LoginDto): Boolean =
            passwordEncoder.matches(loginDto.password, dummyPassword)

}
