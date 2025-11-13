package com.wallet.dto;

import com.wallet.models.UserEntity;

public record SignUpRequest(String email, String password) {
    public UserEntity buildUserEntity() {
        return new UserEntity(email, password);
    }
}
