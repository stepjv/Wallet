package com.wallet.dto.response;

import com.wallet.models.WalletEntity;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class WalletObjResponse {
    private final int id;
    private final String uuid;
    private final String checkNumber;
    private final BigDecimal balance;
    private final String description;
    private final CurrencyObjResponse currency;
    private final ProfileObjResponse profile;

    public WalletObjResponse(WalletEntity w) {
        this.id = w.getId();
        this.uuid = w.getUuid();
        this.checkNumber = w.getCheckNumber();
        this.balance = w.getBalance();
        this.description = w.getDescription();
        this.currency = new CurrencyObjResponse(w.getCurrency());
        this.profile = new ProfileObjResponse(w.getProfile());
    }
}
