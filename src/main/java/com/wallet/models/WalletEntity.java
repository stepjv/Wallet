package com.wallet.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.text.DecimalFormat;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallets")
public class WalletEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "wallet_number")
    private String number;

    @Column(name = "balance")
    private DecimalFormat balance;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToOne
    @JoinColumn(name = "FK_wallet_currency", referencedColumnName = "id")
    private CurrencyEntity currency;

    @OneToOne
    @JoinColumn(name = "FK_wallet_profile", referencedColumnName = "id")
    private ProfileEntity profile;
}
