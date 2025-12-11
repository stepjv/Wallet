package com.wallet.config.entity;

import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileTestObj {
    private int id;
    private String fullName;
    private Instant createdAt;
    private UserTestObj user;

    public ProfileTestObj(int id, UserTestObj user) {
        this.id = id;
        this.user = user;
        this.createdAt = Instant.now();
    }
}
