package com.wallet.config.entity;

import com.wallet.enums.TransactionStatus;
import com.wallet.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionTestObj {
    private int id;
    private String number;
    private BigDecimal transferMoneyCount;
    private TransactionStatus status;
    private TransactionType type;
    private String description;
    private Instant createdAt;
    private WalletTestObj senderWallet;
    private WalletTestObj payeeWallet;
}
