package com.wallet.dto.response;

import com.wallet.enums.status.WalletResponseStatus;

public record WalletIdResultResponse(int walletId, WalletResponseStatus status) {
    public WalletIdResultResponse(int walletId, WalletResponseStatus status) {
        this.walletId = walletId;
        this.status = status;
    }

    public WalletIdResultResponse(WalletResponseStatus status) {
        this(0, status);
    }
}
