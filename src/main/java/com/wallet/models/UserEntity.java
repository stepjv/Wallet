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
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private Instant createdAt;

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
        this.createdAt = Instant.now();
    }

    public UserEntity(int id) {
        this.id = id;
    }

    public ProfileEntity buildProfileEntity() {
        final String name = "guest";
        return new ProfileEntity(name, this);
    }

    @Override
    public String toString() {
        return "UserEntity {" +
                "\n\tid = " + id +
                ", \n\temail = " + email +
                ", \n\tpassword = " + password +
                ", \n\tcreatedAt = " + createdAt +
                "\n}";
    }
}
