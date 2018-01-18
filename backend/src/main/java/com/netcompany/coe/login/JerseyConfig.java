package com.netcompany.coe.login;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("com.netcompany.coe.login.rest", "com.netcompany.coe.login.filters");
    }
}
