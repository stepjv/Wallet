package com.wallet.enums.status;

import lombok.Getter;

@Getter
public enum WalletResponseStatus {
    OK(TransactionResponseStatus.OK),
    CANCELLED_DATA_BASE_ERROR(TransactionResponseStatus.CANCELLED_DATA_BASE_ERROR),
    CANCELLED_BALANCE_EXCEEDED(TransactionResponseStatus.CANCELLED_BALANCE_EXCEEDED),
    CANCELLED_NEGATIVE_BALANCE(TransactionResponseStatus.CANCELLED_NEGATIVE_BALANCE);

    private final TransactionResponseStatus transactionResponseStatus;

    WalletResponseStatus(TransactionResponseStatus transactionResponseStatus) {
        this.transactionResponseStatus = transactionResponseStatus;
    }
}
