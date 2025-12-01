package com.wallet.repositories;

import com.wallet.models.ProfileEntity;
import com.wallet.models.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Integer> {
    @Query("SELECT COUNT(wallet) > 0 FROM WalletEntity wallet WHERE wallet.number = :walletNumber")
    boolean existWithNumber(String walletNumber);

    @Query("SELECT wallet FROM WalletEntity wallet WHERE wallet.profile = :profile")
    WalletEntity findByProfile(ProfileEntity profile);
}
