package com.netcompany.coe.login

import com.netcompany.coe.login.service.JWS_SECRET_KEY_BASE_64
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import java.lang.System.getenv


@Configuration
open class BeanConfig {

    @Bean
    open fun passwordEncoder(): PasswordEncoder = Pbkdf2PasswordEncoder()

    @Bean(JWS_SECRET_KEY_BASE_64)
    open fun jwsSecretKey(): String = getenv(JWS_SECRET_KEY_BASE_64)
}
