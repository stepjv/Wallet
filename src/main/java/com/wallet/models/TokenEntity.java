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
@Table(name = "token")
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

    @Column(name = "expireAt")
    private Instant expireAt;

    @Column(name = "create_at")
    private Instant createAt;

}
