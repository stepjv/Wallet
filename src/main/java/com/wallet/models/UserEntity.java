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

    @Column(name = "role")
    private String role;

    @Column(name = "created_at")
    private Instant createdAt;


    public static UserEntity buildById(int id) {
        return new UserEntity(id);
    }

    public static UserEntity buildNewUser(String email, String password) {
        return new UserEntity(email, password);
    }

    public ProfileEntity buildProfileEntity() {
        final String name = "guest";
        return ProfileEntity.buildNewProfile(name, this);
    }


    private UserEntity(int id) {
        this.id = id;
    }

    private UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = "ROLE_USER";
        this.createdAt = Instant.now();
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
