package com.wallet.services.transactional;

import com.wallet.dto.response.TransactionIdResultResponse;
import com.wallet.enums.TransactionStatus;
import com.wallet.enums.status.TransactionResponseStatus;
import com.wallet.enums.status.WalletResponseStatus;
import com.wallet.models.TransactionEntity;
import com.wallet.repositories.TransactionRepository;
import com.wallet.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wallet.enums.status.TransactionResponseStatus.OK;

@Service
@RequiredArgsConstructor
public class TransactionTransactionalService {

    private final WalletService walletService;
    private final TransactionRepository transactionRepository;

    @Transactional
    public TransactionIdResultResponse replenish(TransactionEntity transaction) {
        final WalletResponseStatus walletResponseStatus = walletService.changeBalance(
                transaction.getPayeeWallet().getId(),
                transaction.getTransferMoneyCount()
        );

        TransactionResponseStatus transactionResponseStatus = walletResponseStatus.getTransactionResponseStatus();

        if (transactionResponseStatus == OK) {
            transaction.setStatus(TransactionStatus.COMPLETED);
        } else {
            transaction.setStatus(TransactionStatus.CANCELLED);
        }

        int transactionId = transactionRepository.save(transaction).getId();
        return new TransactionIdResultResponse(transactionId, transactionResponseStatus);
    }

    @Transactional
    public TransactionIdResultResponse acceptTransfer(TransactionEntity transaction) {
        WalletResponseStatus walletResponseStatus = walletService.changeBalance(
                transaction.getSenderWallet().getId(),
                transaction.getTransferMoneyCount().negate()
        );

        if (walletResponseStatus == WalletResponseStatus.OK) {
            walletResponseStatus = walletService.changeBalance(
                    transaction.getPayeeWallet().getId(),
                    transaction.getTransferMoneyCount()
            );
        }

        final TransactionResponseStatus transactionResponseStatus = walletResponseStatus.getTransactionResponseStatus();

        transaction.setStatus(
                TransactionStatus.getByTransactionResponseStatus(transactionResponseStatus)
        );

        int transactionId = transactionRepository.save(transaction).getId();
        return new TransactionIdResultResponse(transactionId, transactionResponseStatus);
    }
}
