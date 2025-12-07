package com.wallet.services.impl;

import com.wallet.dto.TransactionReplenishmentRequest;
import com.wallet.enums.TransactionStatus;
import com.wallet.models.TransactionEntity;
import com.wallet.repositories.TransactionRepository;
import com.wallet.services.TransactionService;
import com.wallet.services.WalletService;
import com.wallet.util.RandomNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletService walletService;

    @Override
    public int replenish(TransactionReplenishmentRequest request) {

        String transactionNumber = generateUniqueNumber();

        TransactionEntity transaction = request.buildTransactionEntity(transactionNumber);

        boolean replenishResult = walletService.changeBalance(
                transaction.getPayeeWallet().getId(),
                transaction.getMoneyCount()
        );

        if (replenishResult) {
            transaction.setStatus(TransactionStatus.COMPLETED);
        } else {
            transaction.setStatus(TransactionStatus.CANCELLED);
        }

        return transactionRepository.save(transaction).getId();
    }

    private String generateUniqueNumber() {
        String uniqueNumber = RandomNumberGenerator.getTransactionNumber();
        while (transactionRepository.existWithNumber(uniqueNumber)) {
            uniqueNumber = RandomNumberGenerator.getTransactionNumber();
        }
        return uniqueNumber;
    }
}
