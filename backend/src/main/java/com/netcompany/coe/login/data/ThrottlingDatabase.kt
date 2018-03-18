package com.netcompany.coe.login.data

import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

private const val TIME_SPAN_MINUTES = 1L
private const val MAX_ALLOWED_ATTEMPTS_IN_TIME_SPAN = 5

@Repository
class ThrottlingDatabase {

    private val userLoginAttempts = ConcurrentHashMap<String, List<LocalDateTime>>()

    fun isIllegalLoginAttempt(username: String): Boolean {
        return loginAttemptsInTimeSpan(username).count() > MAX_ALLOWED_ATTEMPTS_IN_TIME_SPAN
    }

    fun recordLoginAttempt(username: String) {
        userLoginAttempts[username] = loginAttemptsInTimeSpan(username).plus(LocalDateTime.now())
    }

    fun successfulLogin(username: String) {
        userLoginAttempts.remove(username)
    }

    private fun loginAttemptsInTimeSpan(username: String): List<LocalDateTime> {
        return userLoginAttempts[username]?.filter(::isInTimeSpan) ?: listOf()
    }

}

private fun isInTimeSpan(timestamp: LocalDateTime): Boolean {
    return timestamp.isAfter(LocalDateTime.now().minusMinutes(TIME_SPAN_MINUTES))
}
