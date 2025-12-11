package com.wallet.services;

import com.wallet.dto.request.WalletCreateRequest;
import com.wallet.dto.WalletDTO;
import com.wallet.enums.RequestStatus;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {

    /// API
    int create(int userId, WalletCreateRequest request);

    List<WalletDTO> getAllWalletsByUserId(int userId);

    /// HELP
    RequestStatus canTransfer(int walletId, BigDecimal countOfMoney, boolean transferable);
}
