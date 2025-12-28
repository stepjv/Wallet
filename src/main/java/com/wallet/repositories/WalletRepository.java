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

    @Query("SELECT wallet FROM WalletEntity wallet WHERE wallet.profile.id = :profileId")
    List<WalletEntity> findAllByProfileId(int profileId);

    @Query("SELECT wallet FROM WalletEntity wallet WHERE wallet.id = :id")
    WalletEntity findById(int id);

    @Query("SELECT wallet FROM WalletEntity wallet WHERE wallet.profile.id = :profileId")
    WalletEntity findByProfileId(int profileId);

    @Query("SELECT COUNT(w) = 0 FROM WalletEntity w WHERE w.id = :walletId AND w.profile.id = :profileId")
    boolean isWalletNotOwnedByProfile(int profileId, int walletId);
}
