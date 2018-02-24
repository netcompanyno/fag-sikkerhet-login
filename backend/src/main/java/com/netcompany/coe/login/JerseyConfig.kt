package com.netcompany.coe.login

import org.glassfish.jersey.server.ResourceConfig
import org.springframework.stereotype.Component

@Component
internal class JerseyConfig : ResourceConfig() {
    init {
        packages("com.netcompany.coe.login.rest", "com.netcompany.coe.login.filters")
    }
}
