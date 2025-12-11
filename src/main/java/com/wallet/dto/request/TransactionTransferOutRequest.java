package com.wallet.dto.request;

import com.wallet.enums.TransactionStatus;
import com.wallet.enums.TransactionType;
import com.wallet.models.TransactionEntity;

import java.math.BigDecimal;

public record TransactionTransferOutRequest(int transferOutWalletId, int transferInWalletId, BigDecimal transferAmount, String description) {
    public TransactionEntity buildTransactionEntity(String number) {
        return new TransactionEntity(
                transferOutWalletId,
                transferInWalletId,
                number, transferAmount,
                TransactionStatus.PENDING,
                TransactionType.TRANSFER,
                description
        );
    }
}
