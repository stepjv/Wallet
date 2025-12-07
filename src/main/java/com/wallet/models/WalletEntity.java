package com.wallet.models;

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
@Table(name = "wallet")
public class WalletEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid")
    private String uuid; // для api

    @Column(name = "check") // счет который пользователь видит (чувствительные данные)
    private String check;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToOne
    @JoinColumn(name = "FK_wallet_currency", referencedColumnName = "id")
    private CurrencyEntity currency;

    @OneToOne
    @JoinColumn(name = "FK_wallet_profile", referencedColumnName = "id")
    private ProfileEntity profile;

    public WalletEntity(String number, int currencyId, ProfileEntity profile, String uuid) {
        this.check = number;
        this.createdAt = Instant.now();
        this.currency = new CurrencyEntity(currencyId);
        this.profile = profile;
        this.uuid = uuid;
    }

    public WalletEntity(int id) {
        this.id = id;
    }
}
