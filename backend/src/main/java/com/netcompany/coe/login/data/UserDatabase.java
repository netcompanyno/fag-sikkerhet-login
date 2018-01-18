package com.netcompany.coe.login.data;

import com.netcompany.coe.login.dto.UserDto;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDatabase {
    final List<UserDto> users = Arrays.asList(
            new UserDto("user", "5d22f97379cb22fa39a1dc5cda9fc098dbcaa159021f3510764c12f714fd1801aee94945b045b61d"),
            new UserDto("admin", "2d1d42900d9505e45653759a42ac3c3e8b78b8b684fd6a23530b67de4ddf973462593b6e74cec696")
    );

    public Optional<UserDto> findUser(final String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }
}
