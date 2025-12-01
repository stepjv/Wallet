package com.wallet.dto;

import com.wallet.models.ProfileEntity;
import com.wallet.models.UserEntity;

import java.time.Instant;

public record NewProfileDTO() {
    public ProfileEntity buildProfileEntity(UserEntity user) {
        String name = "guest";
        Instant currentTime = Instant.now();

        return ProfileEntity.builder()
                .fullName(name)
                .createdAt(currentTime)
                .user(user)
                .build();
    }
}
