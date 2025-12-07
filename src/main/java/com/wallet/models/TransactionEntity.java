package com.wallet.models;

import com.wallet.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "transaction_number")
    private String number;

    @Column(name = "transferred_money")
    private BigDecimal moneyCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "FK_transaction_sender_wallet", referencedColumnName = "id")
    private WalletEntity senderWallet;

    @ManyToOne
    @JoinColumn(name = "FK_transaction_payee_wallet", referencedColumnName = "id")
    private WalletEntity payeeWallet;

    public TransactionEntity(String number, BigDecimal moneyCount, TransactionStatus status, String description, int walletId) {
        this.number = number;
        this.moneyCount = moneyCount;
        this.description = description;
        this.payeeWallet = new WalletEntity(walletId);
        this.status = status;
    }
}
