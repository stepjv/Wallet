package com.wallet.dto.response;

import com.wallet.models.WalletEntity;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class WalletResponse {
    private final int id;
    private final String uuid;
    private final String checkNumber;
    private final BigDecimal balance;
    private final String description;
    private final CurrencyResponse currency;
    private final ProfileResponse profile;


    public static WalletResponse build(WalletEntity w) {
        return new WalletResponse(w);
    }


    private WalletResponse(WalletEntity w) {
        this.id = w.getId();
        this.uuid = w.getUuid();
        this.checkNumber = w.getCheckNumber();
        this.balance = w.getBalance();
        this.description = w.getDescription();
        this.currency = new CurrencyResponse(w.getCurrency());
        this.profile = new ProfileResponse(w.getProfile());
    }
}
