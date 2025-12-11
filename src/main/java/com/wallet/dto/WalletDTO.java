package com.wallet.dto;

import com.wallet.models.CurrencyEntity;
import com.wallet.models.ProfileEntity;
import com.wallet.models.WalletEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class WalletDTO {
    private int id;
    private String uuid;
    private String checkNumber;
    private BigDecimal balance;
    private String description;
    private Instant createdAt;
    private CurrencyEntity currency;
    private ProfileEntity profile;

    public WalletDTO(WalletEntity w) {
        this.id = w.getId();
        this.uuid = w.getUuid();
        this.checkNumber = w.getCheckNumber();
        this.balance = w.getBalance();
        this.description = w.getDescription();
        this.createdAt = w.getCreatedAt();
        this.currency = w.getCurrency();
        this.profile = w.getProfile();
    }
}
