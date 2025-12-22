package com.wallet.enums.status;

import lombok.Getter;

@Getter
public enum TransactionResponseStatus {
    OK,
    CANCELLED_DATA_BASE_ERROR,
    CANCELLED_BALANCE_EXCEEDED,
    CANCELLED_NEGATIVE_BALANCE,
    CANCELLED_PROFILE_NOT_OWN_THIS_WALLET,
    CANCELLED_TRANSACTION_NOT_EXIST,
    CANCELLED_NOT_FOR_PENDING;
}
