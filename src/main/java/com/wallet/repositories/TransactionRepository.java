package com.wallet.repositories;

import com.wallet.enums.TransactionStatus;
import com.wallet.models.TransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

    @Query("SELECT COUNT(t) > 0 FROM TransactionEntity t WHERE t.number = :uniqueNumber")
    boolean existWithNumber(String uniqueNumber);

    @Query("SELECT t FROM TransactionEntity t WHERE t.payeeWallet.id = :walletId OR t.senderWallet.id = :walletId")
    List<TransactionEntity> findAllByWalletId(int walletId, Pageable pageable);

    @Query("SELECT t FROM TransactionEntity t where t.payeeWallet.id = :walletId AND t.status = :transactionStatus")
    List<TransactionEntity> findAllByWalletIdAndTransactionStatus(int walletId, TransactionStatus transactionStatus,
                                                                  Pageable pageable);
}
