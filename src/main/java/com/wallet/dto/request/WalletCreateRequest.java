package com.wallet.dto.request;

import com.wallet.models.ProfileEntity;
import com.wallet.models.WalletEntity;
import java.util.UUID;

public record WalletCreateRequest(int currencyId) {
    public WalletEntity buildWalletEntity(String uniqueNumber, ProfileEntity profile, UUID uuid) {
        return WalletEntity.buildNewWallet(uniqueNumber, this.currencyId, profile, uuid.toString());
    }
}
