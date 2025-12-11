package com.wallet.dto.request;

import com.wallet.models.ProfileEntity;
import com.wallet.models.WalletEntity;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletCreateRequest(int currencyId) {
    public WalletEntity buildWalletEntity(String uniqueNumber, ProfileEntity profile, UUID uuid) {

        BigDecimal balance = BigDecimal.ZERO;

        return new WalletEntity(uniqueNumber, this.currencyId, profile, uuid.toString(), balance);
    }
}
