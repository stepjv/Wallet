package com.wallet.repositories;

import com.wallet.models.ProfileEntity;
import com.wallet.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    @Query("SELECT u FROM ProfileEntity u WHERE u.user =:user")
    ProfileEntity findProfileByUser(UserEntity user);
}
