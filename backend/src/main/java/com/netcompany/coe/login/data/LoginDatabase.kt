package com.netcompany.coe.login.data

import com.netcompany.coe.login.dto.UserDto
import org.springframework.stereotype.Repository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Repository
class LoginDatabase {

    private val userSessions = ConcurrentHashMap<UUID, UserDto>()

    tailrec fun createLoginToken(user: UserDto): UUID {
        val token = UUID.randomUUID()
        val current = userSessions.putIfAbsent(token, user)
        return if (current == null) token else createLoginToken(user)
    }

    fun checkToken(token: UUID): UserDto? {
        return userSessions[token]
    }
}
