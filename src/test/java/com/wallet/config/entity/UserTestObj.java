package com.wallet.config.entity;

import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTestObj {
    private int id;
    private String email;
    private String password;
    private Instant createdAt;


    public static UserTestObj fullBuild(int id, String email, String password) {
        return new UserTestObj(id, email, password);
    }


    private UserTestObj(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdAt = Instant.now();
    }
}
