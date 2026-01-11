package com.wallet.config.entity;

import lombok.*;

import java.time.Instant;

@Data
public class UserTestObj {
    private int id;
    private String email;
    private String password;
    private Instant createdAt;


    public static UserTestObj buildWithId(int id, String email, String password) {
        return new UserTestObj(id, email, password);
    }

    public static UserTestObj build(String email, String password) {
        return new UserTestObj(email, password);
    }


    private UserTestObj(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdAt = Instant.now();
    }

    private UserTestObj(String email, String password) {
        this.email = email;
        this.password = password;
        this.createdAt = Instant.now();
    }
}
