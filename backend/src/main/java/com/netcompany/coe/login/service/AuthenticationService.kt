package com.netcompany.coe.login.service

import com.netcompany.coe.login.dto.SecurityStepLoginDto
import com.netcompany.coe.login.dto.UserDto
import com.netcompany.coe.login.dto.UserSettingsDto
import com.netcompany.coe.login.enums.ClaimName
import com.netcompany.coe.login.enums.ClaimName.SECURITY_STEPS
import com.netcompany.coe.login.enums.ClaimName.SECURITY_STEPS_COMPLETED
import com.netcompany.coe.login.enums.SecurityStep
import com.netcompany.coe.login.exceptions.AuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

const val JWS_SECRET_KEY_BASE_64 = "JWS_SECRET_KEY_BASE_64"

@Service
class AuthenticationService(
        @Qualifier(JWS_SECRET_KEY_BASE_64) private val jwsSecretKeyBase64: String
) {

    fun createAuthenticationToken(user: UserDto, userSettings: UserSettingsDto): String {
        val claims = Jwts.claims()
        claims.subject = user.username
        claims[SECURITY_STEPS.jsonName] = userSettings.securitySteps
        claims[SECURITY_STEPS_COMPLETED.jsonName] = emptyList<SecurityStep>()
        return createCompactJws(claims)
    }

    fun findUsername(authenticationToken: String): String {
        return parseClaims(authenticationToken).subject
    }

    fun verifySecurityStepsCompleted(authenticationToken: String) {
        val securitySteps = findSecuritySteps(authenticationToken, SECURITY_STEPS)
        val securityStepsCompleted = findSecuritySteps(authenticationToken, SECURITY_STEPS_COMPLETED)
        val securityStepsNotCompleted = securitySteps.minus(securityStepsCompleted)

        if (securityStepsNotCompleted.isNotEmpty()) {
            throw AuthenticationException("Security step(s) $securityStepsNotCompleted not completed")
        }
    }

    fun createUpdatedAuthenticationToken(securityStepLoginDto: SecurityStepLoginDto): String {
        val claims = parseClaims(securityStepLoginDto.authenticationToken)
        val securityStepsCompleted = findSecuritySteps(claims, SECURITY_STEPS_COMPLETED)

        claims[SECURITY_STEPS_COMPLETED.jsonName] = securityStepsCompleted.plus(securityStepLoginDto.securityStep)
        return createCompactJws(claims)
    }

    private fun findSecuritySteps(authenticationToken: String, claimName: ClaimName) =
            findSecuritySteps(parseClaims(authenticationToken), claimName)

    private fun findSecuritySteps(claims: Claims, claimName: ClaimName): Set<SecurityStep> =
            toSecuritySteps(findClaim(claims, claimName))

    private fun toSecuritySteps(securityStepStrings: List<*>): Set<SecurityStep> {
        return securityStepStrings.map { SecurityStep.valueOf(it.toString()) }.toSet()
    }

    private fun findClaim(claims: Claims, claimName: ClaimName): List<*> {
        return claims[claimName.jsonName] as List<*>
    }

    private fun parseClaims(authenticationToken: String): Claims {
        return try {
            Jwts.parser()
                    .setSigningKey(jwsSecretKeyBase64)
                    .parseClaimsJws(authenticationToken)
                    .body
        } catch (e: Exception) {
            throw AuthenticationException("Problems parsing JWS token [$authenticationToken]", e)
        }
    }

    // TODO Utløpstidspunkt
    private fun createCompactJws(claims: Claims): String {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwsSecretKeyBase64)
                .compact()
    }

}
