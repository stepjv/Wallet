package com.wallet.dto;

import com.wallet.models.ProfileEntity;
import com.wallet.temp.TemporaryGetCurrentUserId;

import java.sql.Timestamp;
import java.time.Instant;

public record AddProfileRequest(String fullName) {

    public ProfileEntity buildProfileEntity() {

        Timestamp createdAt = Timestamp.valueOf(String.valueOf(Instant.now()));
        int currentUserId = TemporaryGetCurrentUserId.getCurrentUserId();

        return new ProfileEntity(fullName, createdAt, currentUserId);
    }
}
