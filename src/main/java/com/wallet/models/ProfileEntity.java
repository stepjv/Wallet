package com.wallet.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "created_at")
    Timestamp createdAt;

    @OneToOne
    @JoinColumn(name = "FK_profile_user", referencedColumnName = "id")
    UserEntity userEntity;
}
