package com.wallet.dto.response;

import com.wallet.enums.RequestStatus;


public record TransactionResponse(int transactionId, RequestStatus status) {
    public TransactionResponse(int transactionId, RequestStatus status) {
        this.transactionId = transactionId;
        this.status = status;
    }
}
