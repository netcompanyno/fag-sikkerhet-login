package com.netcompany.coe.login

import com.netcompany.coe.login.service.JWS_SECRET_KEY_BASE_64
import com.warrenstrange.googleauth.GoogleAuthenticator
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig
import com.warrenstrange.googleauth.IGoogleAuthenticator
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import java.lang.System.getenv


@Configuration
open class BeanConfig(
        @Value("\${totp.windowSize}") private val totpWindowSize: Int
) {

    @Bean
    open fun passwordEncoder(): PasswordEncoder = Pbkdf2PasswordEncoder()

    @Bean(JWS_SECRET_KEY_BASE_64)
    open fun jwsSecretKey(): String = getenv(JWS_SECRET_KEY_BASE_64)

    @Bean
    open fun googleAuthenticator(): IGoogleAuthenticator = GoogleAuthenticator(
            GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder()
                    .setWindowSize(totpWindowSize)
                    .build())
}
