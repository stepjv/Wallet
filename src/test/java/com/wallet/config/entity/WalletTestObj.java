package com.wallet.config.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletTestObj {
    private int id;
    private String uuid;
    private String checkNumber;
    private BigDecimal balance;
    private String description;
    private Instant createdAt;
    private CurrencyTestObj currency;
    private ProfileTestObj profile;

    public WalletTestObj(int id, CurrencyTestObj currency, ProfileTestObj profile) {
        this.id = id;
        this.currency = currency;
        this.profile = profile;
    }
}
