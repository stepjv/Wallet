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
@Table(name = "currency")
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "created_at") // понять в какой момент времени было
    private Instant createdAt;


    public CurrencyEntity(int id) {
        this.id = id;
    }

    public CurrencyEntity(String name, String code) {
        this.name = name;
        this.code = code;
        this.createdAt = Instant.now();
    }
}
