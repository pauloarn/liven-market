package com.liven.market.utils;

import com.liven.market.model.User;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioMock {
    private final User authenticatedUser = new User();

    public static Builder builder() {
        return new Builder();
    }

    public void mockPorUsuarioLogin(String userEmail) {
        authenticatedUser.setEmail(userEmail);
        mockUserAuthenticatedDTO(authenticatedUser);
    }

    private void mockUserAuthenticatedDTO(User loggedUser) {
        var request = new MockHttpServletRequest();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loggedUser, null, loggedUser.getAuthorities());
        authentication.setDetails(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static class Builder {
        private final UsuarioMock usuarioMock = new UsuarioMock();
        private final User userAuth = new User();

        public Builder setEmail(String email) {
            userAuth.setEmail(email);
            return this;
        }

        public Builder setUserName(String userName) {
            userAuth.setName(userName);
            return this;
        }

        public void build() {
            usuarioMock.mockUserAuthenticatedDTO(userAuth);
        }
    }
}

