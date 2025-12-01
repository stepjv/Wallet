package com.wallet.services;

import com.wallet.dto.AddWalletRequest;

public interface WalletService {
    void add(int userId, AddWalletRequest request);
}
