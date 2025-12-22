package com.wallet.dto.response;

import com.wallet.enums.status.TransactionResponseStatus;


public record TransactionIdResponse(int transactionId, TransactionResponseStatus status) {

    public TransactionIdResponse(int transactionId, TransactionResponseStatus status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public TransactionIdResponse(TransactionResponseStatus status) {
        this(0, status);
    }
}
