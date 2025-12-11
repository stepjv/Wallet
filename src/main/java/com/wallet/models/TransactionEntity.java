package com.wallet.models;

import com.wallet.enums.TransactionStatus;
import com.wallet.enums.TransactionType;
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
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "transaction_number")
    private String number;

    @Column(name = "transferred_money")
    private BigDecimal transferMoneyCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType type;

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

    public TransactionEntity(String number, BigDecimal transferMoneyCount, TransactionStatus status, TransactionType type, String description, int walletId) {
        this.number = number;
        this.transferMoneyCount = transferMoneyCount;
        this.description = description;
        this.payeeWallet = new WalletEntity(walletId);
        this.status = status;
        this.type = type;
    }

    public TransactionEntity(int senderWalletId, int payeeWalletId, String number, BigDecimal transferMoneyCount, TransactionStatus status, TransactionType type, String description) {
        this.number = number;
        this.transferMoneyCount = transferMoneyCount;
        this.description = description;
        this.senderWallet = new WalletEntity(senderWalletId);
        this.payeeWallet = new WalletEntity(payeeWalletId);
        this.status = status;
        this.type = type;
    }

    @Override
    public String toString() {
        return "TransactionEntity {" +
                "\n\tid = " + id +
                ", \n\tnumber = " + number +
                ", \n\ttransferMoneyCount = " + transferMoneyCount +
                ", \n\tstatus = " + status +
                ", \n\ttype = " + type +
                ", \n\tdescription = " + description +
                ", \n\tcreatedAt = " + createdAt +
                ", \n\tsenderWallet = " + senderWallet.toString() +
                ", \n\tpayeeWallet = " + payeeWallet.toString() +
                "\n}";
    }
}
