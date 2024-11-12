package com.liven.market.filters;


import com.liven.market.enums.MessageEnum;
import com.liven.market.exceptions.ApiErrorException;
import com.liven.market.model.User;
import com.liven.market.service.UserService;
import com.liven.market.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        validateToken(request);
        filterChain.doFilter(request, response);
    }

    private void validateToken(HttpServletRequest request) {
        String token = recoverToken(request);
        if(Objects.nonNull(token) ){
            try {
                String email = tokenUtils.validateToken(token);
                User user = userService.findUserByEmail(email);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ApiErrorException e) {
                throw new RuntimeException(MessageEnum.INVALID_TOKEN.getMessage());
            }
        }
    }

    private String recoverToken(HttpServletRequest request){
        var authorization = request.getHeader("Authorization");
        if(Objects.isNull(authorization)) return null;
        return authorization.replace("Bearer ", "");
    }
}
