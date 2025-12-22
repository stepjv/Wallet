package com.wallet.services;

import com.wallet.dto.request.WalletCreateRequest;
import com.wallet.dto.response.WalletObjResponse;
import com.wallet.dto.response.WalletListResponse;
import com.wallet.enums.status.WalletResponseStatus;
import com.wallet.models.WalletEntity;

import java.math.BigDecimal;

public interface WalletService {

    /// API
    int create(int userId, WalletCreateRequest request);

    WalletListResponse getAllWalletsByUserId(int userId);

    WalletObjResponse getDTOById(int walletId);

    /// HELP
    WalletResponseStatus changeBalance(int walletId, BigDecimal money);

    WalletResponseStatus canTransfer(int walletId, BigDecimal money);

    WalletEntity getEntityById(int walletId);

    boolean isWalletIdNotOwnedByProfileId(int profileId, int walletId);


}
