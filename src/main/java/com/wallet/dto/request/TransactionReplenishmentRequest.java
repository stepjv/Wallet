package com.wallet.dto.request;

import com.wallet.enums.TransactionStatus;
import com.wallet.enums.TransactionType;
import com.wallet.models.TransactionEntity;

import java.math.BigDecimal;

public record TransactionReplenishmentRequest(int walletId, BigDecimal transferAmount, String description) {
    public TransactionEntity buildTransactionEntity(String number) {
        return TransactionEntity.buildByReplenishRequest(
                number, transferAmount,
                TransactionStatus.PENDING,
                TransactionType.REPLENISHMENT,
                description, walletId
        );
    }
}
