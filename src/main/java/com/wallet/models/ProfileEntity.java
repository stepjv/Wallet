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
@Table(name = "profiles")
public class ProfileEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToOne
    @JoinColumn(name = "FK_profile_user", referencedColumnName = "id")
    private UserEntity user;


    public static ProfileEntity buildNewProfile(String fullName, UserEntity user) {
        return new ProfileEntity(fullName, user);
    }


    private ProfileEntity(String fullName, UserEntity user) {
        this.fullName = fullName;
        this.user = user;
        this.createdAt = Instant.now();
    }


    @Override
    public String toString() {
        return "ProfileEntity {" +
                "\n\tid = " + id +
                ", \n\tfullName = " + fullName +
                ", \n\tcreatedAt = " + createdAt +
                ", \n\tuser = " + user.toString() +
                "\n}";
    }
}
