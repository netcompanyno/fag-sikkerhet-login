package com.netcompany.coe.login.service;

import com.netcompany.coe.login.data.LoginDatabase;
import com.netcompany.coe.login.data.UserDatabase;
import com.netcompany.coe.login.dto.LoginDto;
import com.netcompany.coe.login.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    private final UserDatabase userDatabase;
    private final LoginDatabase loginDatabase;
    private final PasswordEncoder passwordEncoder;
    private final String dummyPassword;

    public LoginService(final UserDatabase userDatabase,
                        final LoginDatabase loginDatabase,
                        final PasswordEncoder passwordEncoder) {
        this.userDatabase = userDatabase;
        this.loginDatabase = loginDatabase;
        this.passwordEncoder = passwordEncoder;
        dummyPassword = passwordEncoder.encode("x");
    }

    public String getUserToken(final LoginDto loginDto) {
        final Optional<UserDto> user = userDatabase.findUser(loginDto.getUsername());

        if(!user.isPresent()) {
            passwordEncoder.matches(loginDto.getPassword(), dummyPassword);
            return null;
        }

        if(passwordEncoder.matches(loginDto.getPassword(), user.get().getPasswordHash())) {
            return loginDatabase.createLoginToken(user.get()).toString();
        }

        return null;
    }
}
