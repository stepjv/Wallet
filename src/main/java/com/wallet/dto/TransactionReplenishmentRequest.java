package com.wallet.dto;

import com.wallet.enums.TransactionStatus;
import com.wallet.models.TransactionEntity;

import java.math.BigDecimal;

public record TransactionReplenishmentRequest(int walletId, BigDecimal countOfMoney, String description) {
    public TransactionEntity buildTransactionEntity(String number) {
        return new TransactionEntity(number, countOfMoney, TransactionStatus.PENDING, description, walletId);
    }
}
