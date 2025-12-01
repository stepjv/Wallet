package com.wallet.models;

import com.wallet.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "transaction_number")
    private String number;

    @Column(name = "transferred_money")
    private String moneyCount;

    @Column(name = "status")
    private Enum<TransactionStatus> status;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToOne
    @JoinColumn(name = "FK_transaction_sender_wallet", referencedColumnName = "id")
    private WalletEntity senderWallet;

    @OneToOne
    @JoinColumn(name = "FK_transaction_payee_wallet", referencedColumnName = "id")
    private WalletEntity payeeWallet;
}
