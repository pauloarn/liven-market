package com.liven.market.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.liven.market.enums.MessageEnum;
import com.liven.market.exceptions.ApiErrorException;
import com.liven.market.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Log4j2
public class TokenUtils {

    @Value("${market.app.secret}")
    private String secret;

    public String generateToken(User user) throws ApiErrorException {
        log.info("Generating auth token");
        try {
            Algorithm ag = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("liven-market")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpireInstant())
                    .sign(ag);

            return token;
        } catch (JWTCreationException e) {
            throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, MessageEnum.FAIL_TO_CREATE_TOKEN);
        }
    }

    public String validateToken(String token) throws ApiErrorException {
        try {
            Algorithm ag = Algorithm.HMAC256(secret);
            return JWT.require(ag)
                    .withIssuer("liven-market")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, MessageEnum.INVALID_TOKEN);
        }
    }

    private Instant generateExpireInstant() {
        return LocalDateTime.now().plusHours(10000000).toInstant(ZoneOffset.of("-03:00"));
    }
}
