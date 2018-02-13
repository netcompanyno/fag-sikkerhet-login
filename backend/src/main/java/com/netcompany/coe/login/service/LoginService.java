package com.netcompany.coe.login.service;

import com.netcompany.coe.login.data.LoginDatabase;
import com.netcompany.coe.login.data.ThrottlingDatabase;
import com.netcompany.coe.login.data.UserDatabase;
import com.netcompany.coe.login.dto.LoginDto;
import com.netcompany.coe.login.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoginService {

    private final UserDatabase userDatabase;
    private final LoginDatabase loginDatabase;
    private final PasswordEncoder passwordEncoder;
    private final ThrottlingDatabase throttlingDatabase;
    private final String dummyPassword;

    public LoginService(
            UserDatabase userDatabase,
            LoginDatabase loginDatabase,
            PasswordEncoder passwordEncoder,
            ThrottlingDatabase throttlingDatabase) {
        this.userDatabase = userDatabase;
        this.loginDatabase = loginDatabase;
        this.passwordEncoder = passwordEncoder;
        this.throttlingDatabase = throttlingDatabase;
        dummyPassword = passwordEncoder.encode("x");
    }

    public String getUserToken(final LoginDto loginDto) {
        final Optional<UserDto> user = userDatabase.findUser(loginDto.getUsername());

        if (!user.isPresent()) {
            fakePasswordCheck(loginDto);
            return null;
        }

        if (throttlingDatabase.isIllegalLoginAttempt(user.get().getUsername())) {
            fakePasswordCheck(loginDto);
            throttlingDatabase.recordLoginAttempt(user.get().getUsername());
            System.err.println("(" + LocalDateTime.now() + ") Throttling user [" + user.get().getUsername() + "]");
            return null;
        }

        if (passwordEncoder.matches(loginDto.getPassword(), user.get().getPasswordHash())) {
            throttlingDatabase.successfulLogin(user.get().getUsername());
            return loginDatabase.createLoginToken(user.get()).toString();
        }

        throttlingDatabase.recordLoginAttempt(user.get().getUsername());
        return null;
    }

    private boolean fakePasswordCheck(LoginDto loginDto) {
        return passwordEncoder.matches(loginDto.getPassword(), dummyPassword);
    }

}
