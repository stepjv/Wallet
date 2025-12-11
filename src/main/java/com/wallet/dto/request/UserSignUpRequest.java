package com.wallet.dto.request;

import com.wallet.models.UserEntity;

public record UserSignUpRequest(String email, String password) {
    public UserEntity buildUserEntity() {
        return new UserEntity(email, password);
    }
}
