package com.wallet.enums;

import com.wallet.enums.status.TransactionResponseStatus;

public enum TransactionStatus {
    COMPLETED,
    PENDING,
    CANCELLED;

    public static TransactionStatus getByTransactionResponseStatus(TransactionResponseStatus responseStatus) {
        if (responseStatus == TransactionResponseStatus.OK) {
            return TransactionStatus.COMPLETED;
        } else {
            return TransactionStatus.CANCELLED;
        }

    }
}
