package com.wallet.repositories;

import com.wallet.models.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

    @Query("SELECT COUNT(t) > 0 FROM TransactionEntity t WHERE t.number = :uniqueNumber")
    boolean existWithNumber(String uniqueNumber);
}
