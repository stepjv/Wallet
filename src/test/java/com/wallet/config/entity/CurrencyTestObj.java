package com.wallet.config.entity;

import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyTestObj {
    private int id;
    private String name;
    private String code;
    private Instant createdAt;

    public CurrencyTestObj(int id) {
        this.id = id;
        this.createdAt = Instant.now();
    }
}
