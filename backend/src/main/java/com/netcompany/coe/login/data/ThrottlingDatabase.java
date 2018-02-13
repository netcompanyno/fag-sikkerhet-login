package com.netcompany.coe.login.data;

import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

@Repository
public class ThrottlingDatabase {

    private static final int TIME_SPAN_MINUTES = 1;
    private static final int MAX_ALLOWED_ATTEMPTS_IN_TIME_SPAN = 5;

    private final Map<String, List<LocalDateTime>> userLoginAttempts = new ConcurrentHashMap<>();

    public boolean isIllegalLoginAttempt(String username) {
        return loginAttemptsInTimeSpan(username)
                .count() > MAX_ALLOWED_ATTEMPTS_IN_TIME_SPAN;
    }

    public void recordLoginAttempt(String username) {
        userLoginAttempts.put(
                username,
                ImmutableList.<LocalDateTime>builder()
                        .addAll(loginAttemptsInTimeSpan(username)
                                .iterator())
                        .add(LocalDateTime.now())
                        .build());
    }

    public void successfulLogin(String username) {
        userLoginAttempts.remove(username);
    }

    private Stream<LocalDateTime> loginAttemptsInTimeSpan(String username) {
        return userLoginAttempts.getOrDefault(username, emptyList()).stream()
                .filter(ThrottlingDatabase::isInTimeSpan);
    }

    private static boolean isInTimeSpan(LocalDateTime timestamp) {
        return timestamp.isAfter(LocalDateTime.now().minusMinutes(TIME_SPAN_MINUTES));
    }
}
