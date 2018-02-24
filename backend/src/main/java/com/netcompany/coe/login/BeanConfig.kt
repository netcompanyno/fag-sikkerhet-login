package com.netcompany.coe.login

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder

@Configuration
open class BeanConfig {

    @Bean
    open fun passwordEncoder(): PasswordEncoder = Pbkdf2PasswordEncoder()
}
