package com.wallet.config.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
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
