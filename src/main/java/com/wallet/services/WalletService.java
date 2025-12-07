package com.wallet.services;

import com.wallet.dto.WalletCreateRequest;
import com.wallet.models.WalletEntity;

import java.math.BigDecimal;

public interface WalletService {

    /// API
    int create(int userId, WalletCreateRequest request);
    WalletEntity getWalletByUserId(int userId);

    /// HELP
    boolean changeBalance(int walletId, BigDecimal countOfMoney);
}
