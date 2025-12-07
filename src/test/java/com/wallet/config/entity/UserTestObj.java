package com.wallet.config.entity;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTestObj {
    private int id;
    private String email;
    private String password;
    private Instant createdAt;

    public UserTestObj(int id) {
        this.id = id;
        this.createdAt = Instant.now();
    }
}
