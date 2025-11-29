package com.wallet.repositories;

import com.wallet.models.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Integer> {
    @Query("SELECT COUNT(u) > 0 FROM WalletEntity u WHERE u.number = :walletNumber")
    boolean existWalletWithNumber(String walletNumber);
}
