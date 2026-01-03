package com.wallet.enums;

import com.wallet.enums.status.TransactionResponseStatus;
import lombok.Getter;

@Getter
public enum TransactionStatus {
    COMPLETED("completed"),
    PENDING("pending"),
    CANCELLED("cancelled");

    private final String statusValue;


    public static TransactionStatus getByTransactionResponseStatus(TransactionResponseStatus responseStatus) {
        if (responseStatus == TransactionResponseStatus.OK) {
            return TransactionStatus.COMPLETED;
        } else {
            return TransactionStatus.CANCELLED;
        }
    }

    public static TransactionStatus fromString(String status) {
        return valueOf(status);
    }


    TransactionStatus(String statusValue) {
        this.statusValue = statusValue;
    }
}
