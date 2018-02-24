package com.netcompany.coe.login.data

import com.netcompany.coe.login.dto.UserDto
import org.springframework.stereotype.Repository

@Repository
class UserDatabase {

    private val users = listOf(
            UserDto(
                    username = "user",
                    passwordHash = "5d22f97379cb22fa39a1dc5cda9fc098dbcaa159021f3510764c12f714fd1801aee94945b045b61d"
            ),
            UserDto(
                    username = "admin",
                    passwordHash = "2d1d42900d9505e45653759a42ac3c3e8b78b8b684fd6a23530b67de4ddf973462593b6e74cec696"
            )
    )

    fun findUser(username: String): UserDto? {
        return users.firstOrNull { user -> user.username.equals(username, ignoreCase = true) }
    }
}
