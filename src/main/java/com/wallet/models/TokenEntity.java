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
@Table(name = "tokens")
public class TokenEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "token")
    private String token;

    @Column(name = "expire_at")
    private Instant expireAt;

    @Column(name = "create_at")
    private Instant createAt;


    public static TokenEntity build(int userId, String token) {
        return new TokenEntity(userId, token);
    }


    private TokenEntity(int userId, String token) {
        this.user = UserEntity.buildById(userId);
        this.token = token;
        this.createAt = Instant.now();
    }
}
