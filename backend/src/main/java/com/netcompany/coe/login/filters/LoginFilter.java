package com.netcompany.coe.login.filters;

import com.netcompany.coe.login.UserBean;
import com.netcompany.coe.login.data.LoginDatabase;
import com.netcompany.coe.login.dto.UserDto;
import com.netcompany.coe.login.exceptions.AccessDeniedException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Optional;

@Provider
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class LoginFilter implements ContainerRequestFilter {

    private final UserBean userBean;
    private final LoginDatabase loginDatabase;

    @Override
    public void filter(final ContainerRequestContext containerRequestContext) throws IOException {
        final String authHeader = containerRequestContext.getHeaderString("Authorization");

        if (authHeader == null) {
            return;
        }

        final Optional<UserDto> userDto = loginDatabase.checkToken(authHeader);

        if (!userDto.isPresent()) {
            throw new AccessDeniedException("Invalid token");
        }

        userBean.setUser(userDto.get().getUsername());
    }
}
