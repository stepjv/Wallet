package com.wallet.dto.response;

import com.wallet.enums.status.TransactionResponseStatus;


public record TransactionResponse(int transactionId, TransactionResponseStatus status) {

    public TransactionResponse(int transactionId, TransactionResponseStatus status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public TransactionResponse(TransactionResponseStatus status) {
        this(0, status);
    }
}
