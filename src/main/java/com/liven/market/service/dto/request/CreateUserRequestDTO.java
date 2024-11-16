package com.liven.market.service.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserRequestDTO {

    @Email
    @NotNull
    @Size(min = 2, max = 255)
    private String email;

    @NotNull
    private String name;

    @NotNull
    @Size(min = 6)
    private String password;
}
