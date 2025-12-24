package com.wallet.dto.response;

import com.wallet.enums.status.TransactionResponseStatus;


public record TransactionIdResultResponse(int transactionId, TransactionResponseStatus status) {

    public TransactionIdResultResponse(int transactionId, TransactionResponseStatus status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public TransactionIdResultResponse(TransactionResponseStatus status) {
        this(0, status);
    }
}
