package com.liven.market.service.dto.response;

import com.liven.market.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailResponseDTO {
    private String name;
    private String email;

    public UserDetailResponseDTO(User user){
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
