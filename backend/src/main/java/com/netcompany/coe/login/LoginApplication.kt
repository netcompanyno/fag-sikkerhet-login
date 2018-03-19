package com.netcompany.coe.login

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAutoConfiguration(exclude = [ErrorMvcAutoConfiguration::class])
class LoginApplication

fun main(args: Array<String>) {
    runApplication<LoginApplication>(*args)
}
