package com.wallet.services.impl;

import com.wallet.dto.request.TransactionReplenishmentRequest;
import com.wallet.dto.request.TransactionTransferOutRequest;
import com.wallet.dto.response.TransactionResponse;
import com.wallet.dto.TransactionDTO;
import com.wallet.enums.RequestStatus;
import com.wallet.enums.TransactionStatus;
import com.wallet.models.TransactionEntity;
import com.wallet.repositories.TransactionRepository;
import com.wallet.services.TransactionService;
import com.wallet.services.WalletService;
import com.wallet.util.RandomNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.wallet.enums.RequestStatus.OK;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletService walletService;

    @Override
    public TransactionResponse replenish(TransactionReplenishmentRequest request) {

        String transactionNumber = generateUniqueNumber();

        TransactionEntity transaction = request.buildTransactionEntity(transactionNumber);

        RequestStatus replenishResult = walletService.canTransfer(
                transaction.getPayeeWallet().getId(),
                transaction.getTransferMoneyCount(),
                true
        );

        if (replenishResult == OK) {
            transaction.setStatus(TransactionStatus.COMPLETED);
        } else {
            transaction.setStatus(TransactionStatus.CANCELLED);
        }

        int transactionId = transactionRepository.save(transaction).getId();
        return new TransactionResponse(transactionId, replenishResult);
    }

    @Override
    public List<TransactionDTO> getAllByWalletId(int walletId) { // возвращать DTO

        List<TransactionEntity> transactions = transactionRepository.findAllByWalletId(walletId);
        List<TransactionDTO> transactionsDTO = new ArrayList<>();

        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }

        for (TransactionEntity transaction : transactions) {
            transactionsDTO.add(new TransactionDTO(transaction));
        }

        return transactionsDTO;
    }

    @Override
    public TransactionResponse sendTransferOutRequest(TransactionTransferOutRequest request) {
        TransactionEntity transaction = request.buildTransactionEntity(generateUniqueNumber());

        RequestStatus requestStatus = walletService.canTransfer(
                transaction.getSenderWallet().getId(),
                transaction.getTransferMoneyCount().negate(),
                false
        );

        if (requestStatus == OK) {
            transaction.setStatus(TransactionStatus.PENDING);
            return new TransactionResponse(transactionRepository.save(transaction).getId(), OK);
        }

        transaction.setStatus(TransactionStatus.CANCELLED);
        return new TransactionResponse(
                transactionRepository.save(transaction).getId(), requestStatus
        );
    }

    // TODO
    @Override
    public int acceptTransferOutRequest() {
        return 0;
    }


    private String generateUniqueNumber() {
        String uniqueNumber = RandomNumberGenerator.getTransactionNumber();
        while (transactionRepository.existWithNumber(uniqueNumber)) {
            uniqueNumber = RandomNumberGenerator.getTransactionNumber();
        }
        return uniqueNumber;
    }
}
