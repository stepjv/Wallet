package com.wallet.dto.response;

import com.wallet.enums.TransactionStatus;
import com.wallet.enums.TransactionType;
import com.wallet.models.TransactionEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class TransactionResponse {
    private final int id;
    private final String number;
    private final BigDecimal moneyCount;
    private final TransactionStatus status;
    private final TransactionType type;
    private final String description;
    private final Instant createdAt;
    private final WalletResponse senderWallet;
    private final WalletResponse payeeWallet;

    public TransactionResponse(TransactionEntity t) {
        this.id = t.getId();
        this.number = t.getNumber();
        this.moneyCount = t.getTransferMoneyCount();
        this.status = t.getStatus();
        this.type = t.getType();
        this.description = t.getDescription();
        this.createdAt = t.getCreatedAt();
        this.senderWallet = new WalletResponse(t.getSenderWallet());
        this.payeeWallet = new WalletResponse(t.getPayeeWallet());
    }
}

