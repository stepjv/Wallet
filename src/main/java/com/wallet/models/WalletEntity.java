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
@Table(name = "wallets")
public class WalletEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid")
    private String uuid; // для api

    @Column(name = "check_number") // счет который пользователь видит (чувствительные данные)
    private String checkNumber;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "FK_wallet_currency", referencedColumnName = "id")
    private CurrencyEntity currency;

    @ManyToOne
    @JoinColumn(name = "FK_wallet_profile", referencedColumnName = "id")
    private ProfileEntity profile;

    public WalletEntity(String number, int currencyId, ProfileEntity profile, String uuid, BigDecimal balance) {
        this.checkNumber = number;
        this.createdAt = Instant.now();
        this.currency = new CurrencyEntity(currencyId);
        this.profile = profile;
        this.uuid = uuid;
        this.balance = balance;
    }

    public WalletEntity(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "WalletEntity {" +
                "\n\tid = " + id +
                ", \n\tuuid = " + uuid +
                ", \n\tcheckNumber = " + checkNumber +
                ", \n\tbalance = " + balance +
                ", \n\tdescription = " + description +
                ", \n\tcreatedAt = " + createdAt +
                ", \n\tcurrency = " + currency.toString() +
                ", \n\tprofile = " + profile.toString() +
                "\n}";
    }
}
