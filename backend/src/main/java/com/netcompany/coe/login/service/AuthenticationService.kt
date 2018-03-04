package com.netcompany.coe.login.service

import com.netcompany.coe.login.dto.UserDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

const val JWS_SECRET_KEY_BASE_64 = "JWS_SECRET_KEY_BASE_64"

@Service
class AuthenticationService(
        @Qualifier(JWS_SECRET_KEY_BASE_64) private val jwsSecretKeyBase64: String
) {
    private val log = LoggerFactory.getLogger(this.javaClass.name)

    fun createAuthenticationToken(user: UserDto): String =
            Jwts.builder()
                    .setSubject(user.username)
                    .signWith(SignatureAlgorithm.HS512, jwsSecretKeyBase64)
                    .compact()

    fun findUserName(compactUserJws: String): String? {
        return try {
            Jwts.parser()
                    .setSigningKey(jwsSecretKeyBase64)
                    .parseClaimsJws(compactUserJws)
                    .body
                    .subject
        } catch (e: SignatureException) {
            log.warn("JWS token tampered with: {}", compactUserJws, e)
            null
        }
    }

}