package com.liven.market.service.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequestDTO {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    @Size(min=6)
    private String password;
}
