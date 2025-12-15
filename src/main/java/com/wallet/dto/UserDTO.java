package com.wallet.dto;

import com.wallet.models.UserEntity;
import lombok.Data;

@Data
public class UserDTO {
    private final int id;
    private final String email;

    public UserDTO(UserEntity u) {
        this.email = u.getEmail();
        this.id = u.getId();
    }
}
