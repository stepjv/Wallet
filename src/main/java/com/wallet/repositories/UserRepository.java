package com.wallet.repositories;

import com.wallet.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.email = :email")
    boolean existUserByEmail(String email);

    @Query("SELECT t.user FROM TokenEntity t WHERE t.token = :token")
    UserEntity findUserByToken(String token);
}
