package com.liven.market.service.dto.response;

import com.liven.market.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDetailResponseDTO {
    private UUID userId;
    private String name;
    private String email;

    public UserDetailResponseDTO(User user) {
        this.name = user.getName();
        this.userId = user.getUserId();
        this.email = user.getEmail();
    }
}
