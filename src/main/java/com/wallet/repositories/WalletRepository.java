package com.wallet.repositories;

import com.wallet.models.ProfileEntity;
import com.wallet.models.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Integer> {

    @Query("SELECT COUNT(wallet) > 0 FROM WalletEntity wallet WHERE wallet.checkNumber = :checkNumber")
    boolean existWithCheck(String checkNumber);

    @Query("SELECT wallet FROM WalletEntity wallet WHERE wallet.profile = :profile")
    List<WalletEntity> findAllByProfile(ProfileEntity profile);

    @Query("SELECT wallet FROM WalletEntity wallet WHERE wallet.id = :id")
    WalletEntity findById(int id);

    WalletEntity findByProfileId(int profileId);
}
