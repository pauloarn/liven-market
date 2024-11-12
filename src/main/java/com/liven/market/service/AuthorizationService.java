package com.liven.market.service;

import com.liven.market.exceptions.ApiErrorException;
import com.liven.market.model.User;
import com.liven.market.service.dto.request.CreateSessionRequestDTO;
import com.liven.market.service.dto.response.SessionResponseDTO;
import com.liven.market.service.dto.response.UserDetailResponseDTO;
import com.liven.market.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizationService implements UserDetailsService {

    // TODO: adicionado o Lazy por problema de dependência circular. Necessário refatorar!!!
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserService userService;
    private final TokenUtils tokenUtils;

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            return userService.findUserByEmail(s);
        } catch (ApiErrorException e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public UserDetailResponseDTO getLoggerUserDTO(){
        return new UserDetailResponseDTO(userService.getLoggedUser());
    }

    public SessionResponseDTO createSession(CreateSessionRequestDTO createSessionRequestDTO) throws ApiErrorException {
        UsernamePasswordAuthenticationToken userNamePassword = new UsernamePasswordAuthenticationToken(createSessionRequestDTO.getUserEmail(), createSessionRequestDTO.getUserPassword());
        var auth = authenticationManager.authenticate(userNamePassword);
        var token = tokenUtils.generateToken((User) auth.getPrincipal());
        return new SessionResponseDTO(token);
    }
}
