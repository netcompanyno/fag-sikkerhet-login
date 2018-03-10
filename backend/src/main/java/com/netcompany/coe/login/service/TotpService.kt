package com.netcompany.coe.login.service

import com.netcompany.coe.login.data.TotpDatabase
import com.netcompany.coe.login.dto.SecurityStepLoginDto
import com.netcompany.coe.login.exceptions.AuthenticationException
import com.warrenstrange.googleauth.IGoogleAuthenticator
import org.springframework.stereotype.Service


/**
 * Provides two-factor authentication by Time-based One-time Password
 */
@Service
class TotpService(
        private val authenticationService: AuthenticationService,
        private val totpDatabase: TotpDatabase,
        private val googleAuthenticator: IGoogleAuthenticator
) {

    fun createAuthenticationToken(securityStepLoginDto: SecurityStepLoginDto): String {
        validateTotp(securityStepLoginDto)
        return authenticationService.createUpdatedAuthenticationToken(securityStepLoginDto)
    }

    private fun validateTotp(securityStepLoginDto: SecurityStepLoginDto) {
        val username = authenticationService.findUsername(securityStepLoginDto.authenticationToken)
        val sharedSecret = totpDatabase.findSharedSecret(username)

        if (!googleAuthenticator.authorize(sharedSecret, securityStepLoginDto.password.toInt())) {
            val securityStep = securityStepLoginDto.securityStep
            throw AuthenticationException("Failed two-factor authentication by [$securityStep] for user [$username]")
        }
    }

}