package com.liven.market.controller;

import com.liven.market.exceptions.ApiErrorException;
import com.liven.market.service.AuthorizationService;
import com.liven.market.service.UserService;
import com.liven.market.service.dto.request.CreateSessionRequestDTO;
import com.liven.market.service.dto.request.CreateUserRequestDTO;
import com.liven.market.service.dto.response.Response;
import com.liven.market.service.dto.response.SessionResponseDTO;
import com.liven.market.service.dto.response.UserDetailResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AuthorizationService authorizationService;

    @PostMapping("create")
    public Response<UserDetailResponseDTO> createUser(
            @RequestBody @Valid CreateUserRequestDTO createUserRequestDTO
    ) throws ApiErrorException {
        Response<UserDetailResponseDTO> response = new Response<>();
        response.setData(userService.createUser(createUserRequestDTO));
        return response.setOk();
    }

    @PostMapping("/create-session")
    public Response<SessionResponseDTO> createSession(
            @RequestBody @Valid CreateSessionRequestDTO createSessionRequestDTO
    ) throws ApiErrorException {
        Response<SessionResponseDTO> response = new Response<>();
        response.setData(authorizationService.createSession(createSessionRequestDTO));
        return response.setOk();
    }

    @GetMapping("/me")
    public Response<UserDetailResponseDTO> getMe() {
        Response<UserDetailResponseDTO> response = new Response<>();
        response.setData(authorizationService.getLoggerUserDTO());
        response.setOk();
        return response;
    }
}
