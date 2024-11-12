package com.liven.market.controller;

import com.liven.market.exceptions.ApiErrorException;
import com.liven.market.service.UserService;
import com.liven.market.service.dto.request.CreateUserRequestDTO;
import com.liven.market.service.dto.response.Response;
import com.liven.market.service.dto.response.UserDetailResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public Response<UserDetailResponseDTO> getLoggedUserData(
    ){
        Response<UserDetailResponseDTO> response = new Response<>();
        response.setOk();
        response.setData(userService.getUserInfo());
        return response;
    }

    @PostMapping()
    public Response<UserDetailResponseDTO> createUser(
            @RequestBody @Valid CreateUserRequestDTO createUserRequestDTO
    ) throws ApiErrorException {
        Response<UserDetailResponseDTO> response = new Response<>();
        response.setData(userService.createUser(createUserRequestDTO));
        return response.setOk();
    }
}
