package com.wallet.dto.response;

import com.wallet.models.UserEntity;
import lombok.Data;

@Data
public class UserObjResponse {
    private final int id;
    private final String email;

    public UserObjResponse(UserEntity u) {
        this.email = u.getEmail();
        this.id = u.getId();
    }
}
