package com.wallet.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currencies")
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "created_at")
    private Instant createdAt;


    public static CurrencyEntity buildById(int id) {
        return new CurrencyEntity(id);
    }

    public static CurrencyEntity buildNewCurrency(String name, String code) {
        return new CurrencyEntity(name, code);
    }


    private CurrencyEntity(int id) {
        this.id = id;
    }

    private CurrencyEntity(String name, String code) {
        this.name = name;
        this.code = code;
        this.createdAt = Instant.now();
    }


    @Override
    public String toString() {
        return "CurrencyEntity {" +
                "\n\tid = " + id +
                ", \n\tname = " + name +
                ", \n\tcode = " + code +
                ", \n\tcreatedAt = " + createdAt +
                "\n}";
    }
}
