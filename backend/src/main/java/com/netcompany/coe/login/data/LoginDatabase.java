package com.netcompany.coe.login.data;

import com.netcompany.coe.login.dto.UserDto;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LoginDatabase {

    private final Map<UUID, UserDto> userSessions = new ConcurrentHashMap<>();

    public synchronized UUID createLoginToken(final UserDto user) {
        final UUID token = UUID.randomUUID();
        if (userSessions.containsKey(token)) {
            return createLoginToken(user);
        }
        userSessions.put(token, user);
        return token;
    }

    public synchronized Optional<UserDto> checkToken(final String token) {
        UUID tokenUuid = UUID.fromString(token);
        return Optional.ofNullable(userSessions.get(tokenUuid));
    }
}
