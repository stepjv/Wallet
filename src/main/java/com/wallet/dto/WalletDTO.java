package com.wallet.dto;

import com.wallet.models.WalletEntity;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class WalletDTO {
    private final int id;
    private final String uuid;
    private final String checkNumber;
    private final BigDecimal balance;
    private final String description;
    private final CurrencyDTO currency;
    private final ProfileDTO profile;

    public WalletDTO(WalletEntity w) {
        this.id = w.getId();
        this.uuid = w.getUuid();
        this.checkNumber = w.getCheckNumber();
        this.balance = w.getBalance();
        this.description = w.getDescription();
        this.currency = new CurrencyDTO(w.getCurrency());
        this.profile = new ProfileDTO(w.getProfile());
    }
}
