package com.wallet.dto;

import com.wallet.models.UserEntity;


import java.time.Instant;

public record SignUpRequest(String email, String password) {
    public UserEntity buildUserEntity() {

        Instant createdAt = Instant.now();

        return new UserEntity(email, password, createdAt);
    }
}
