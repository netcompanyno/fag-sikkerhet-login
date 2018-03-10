package com.netcompany.coe.login.data

import org.springframework.stereotype.Repository

@Repository
class TotpDatabase {

    private val usernameToSharedSecret = mapOf(
            "admin" to "V7URS2QJEDQFV5SE"
    )

    fun findSharedSecret(username: String): String {
        return usernameToSharedSecret[username.toLowerCase()]
                ?: throw IllegalArgumentException("Shared secret not found for $username")
    }
}
