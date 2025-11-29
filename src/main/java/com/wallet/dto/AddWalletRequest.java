package com.wallet.dto;

import com.wallet.models.ProfileEntity;
import com.wallet.models.WalletEntity;

import java.sql.Timestamp;
import java.time.Instant;

public record AddWalletRequest(int currencyId) {
    public WalletEntity buildWalletEntity(String uniqueNumber, ProfileEntity profile) {

        Timestamp createdAt = Timestamp.valueOf(String.valueOf(Instant.now()));

        return new WalletEntity(uniqueNumber, createdAt, this.currencyId, profile);
    }
}
