package com.liven.market.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateSessionRequestDTO {
    @Email
    @NotNull
    private String userEmail;

    @NotNull
    @Size(min=6)
    private String userPassword;
}
