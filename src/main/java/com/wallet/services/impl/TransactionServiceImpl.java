package com.wallet.services.impl;

import com.wallet.dto.request.TransactionGetByIdRequest;
import com.wallet.dto.request.TransactionGetByWalletIdRequest;
import com.wallet.dto.request.TransactionReplenishmentRequest;
import com.wallet.dto.request.TransactionTransferRequest;
import com.wallet.dto.response.TransactionListResponse;
import com.wallet.dto.response.TransactionIdResponse;
import com.wallet.dto.response.TransactionObjResponse;
import com.wallet.enums.TransactionStatus;
import com.wallet.enums.status.TransactionResponseStatus;
import com.wallet.enums.status.WalletResponseStatus;
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
import java.util.Optional;

import static com.wallet.enums.status.TransactionResponseStatus.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletService walletService;

    @Override
    public TransactionIdResponse replenish(int profileId, TransactionReplenishmentRequest request) {

        if (walletService.isWalletIdNotOwnedByProfileId(profileId, request.walletId())) {
            return new TransactionIdResponse(TransactionResponseStatus.CANCELLED_PROFILE_NOT_OWN_WALLET);
        }

        String transactionNumber = generateUniqueNumber();
        TransactionEntity transaction = request.buildTransactionEntity(transactionNumber);

        WalletResponseStatus walletResponseStatus = walletService.changeBalance(
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
        return new TransactionIdResponse(transactionId, transactionResponseStatus);
    }

    public TransactionListResponse getAllByWalletId(int profileId, TransactionGetByWalletIdRequest request) {

        if (walletService.isWalletIdNotOwnedByProfileId(profileId, request.walletId())) {
            return new TransactionListResponse(Collections.emptyList(), CANCELLED_PROFILE_NOT_OWN_WALLET);
        }

        List<TransactionEntity> transactions = transactionRepository.findAllByWalletId(request.walletId());
        if (transactions.isEmpty()) {
            return new TransactionListResponse(Collections.emptyList(), OK) ;
        }

        List<TransactionObjResponse> transactionsDTO = new ArrayList<>();
        for (TransactionEntity transaction : transactions) {
            transactionsDTO.add(new TransactionObjResponse(transaction));
        }

        return new TransactionListResponse(transactionsDTO, OK);
    }

    @Override
    public TransactionIdResponse sendTransferRequest(int profileId, TransactionTransferRequest request) {

        if(walletService.isWalletIdNotOwnedByProfileId(profileId, request.transferOutWalletId())) {
            return new TransactionIdResponse(CANCELLED_PROFILE_NOT_OWN_WALLET);
        }

        TransactionEntity transaction = request.buildTransactionEntity(generateUniqueNumber());

        WalletResponseStatus walletResponseStatus = walletService.canTransfer(
                transaction.getSenderWallet().getId(),
                transaction.getTransferMoneyCount().negate()
        );

        TransactionResponseStatus transactionResponseStatus = walletResponseStatus.getTransactionResponseStatus();

        if (transactionResponseStatus == OK) {
            transaction.setStatus(TransactionStatus.PENDING);
        } else {
            transaction.setStatus(TransactionStatus.CANCELLED);
        }

        int transactionId = transactionRepository.save(transaction).getId();

        return new TransactionIdResponse(transactionId, transactionResponseStatus);
    }

    @Override
    public TransactionListResponse getPendingTransferRequestsByWalletId(int profileId, TransactionGetByWalletIdRequest request) {

        if (walletService.isWalletIdNotOwnedByProfileId(profileId, request.walletId())) {
            return new TransactionListResponse(Collections.emptyList(), CANCELLED_PROFILE_NOT_OWN_WALLET);
        }

        List<TransactionEntity> transactions = transactionRepository.findAllByWalletIdAndTransactionStatus(
                request.walletId(), TransactionStatus.PENDING
        );
        if (transactions.isEmpty()) {
            return new TransactionListResponse(Collections.emptyList(), OK) ;
        }

        List<TransactionObjResponse> transactionsDTO = new ArrayList<>();
        for (TransactionEntity transaction : transactions) {
            transactionsDTO.add(new TransactionObjResponse(transaction));
        }

        return new TransactionListResponse(transactionsDTO, OK);
    }

    @Override
    public TransactionIdResponse acceptTransfer(int profileId, TransactionGetByIdRequest request) {
        Optional<TransactionEntity> transaction = transactionRepository.findById(request.transactionId());
        if (transaction.isEmpty()) {
            return new TransactionIdResponse(CANCELLED_TRANSACTION_NOT_EXIST);
        }
        int payeeWalletId = transaction.get().getPayeeWallet().getId();

        if (walletService.isWalletIdNotOwnedByProfileId(profileId, payeeWalletId)) {
            return new TransactionIdResponse(CANCELLED_PROFILE_NOT_OWN_WALLET);
        }

        WalletResponseStatus walletResponseStatus = walletService.changeBalance(
                transaction.get().getSenderWallet().getId(), transaction.get().getTransferMoneyCount().negate()
        );

        if (walletResponseStatus == WalletResponseStatus.OK) {
            walletResponseStatus = walletService.changeBalance(
                    payeeWalletId, transaction.get().getTransferMoneyCount()
            );
        }

        TransactionResponseStatus transactionResponseStatus = walletResponseStatus.getTransactionResponseStatus();

        transaction.get().setStatus(TransactionStatus.getByTransactionResponseStatus(transactionResponseStatus));

        int transactionId = transactionRepository.save(transaction.get()).getId();
        return new TransactionIdResponse(transactionId, transactionResponseStatus);
    }

    /// INTERNAL HELP

    private String generateUniqueNumber() {
        String uniqueNumber = RandomNumberGenerator.getTransactionNumber();
        while (transactionRepository.existWithNumber(uniqueNumber)) {
            uniqueNumber = RandomNumberGenerator.getTransactionNumber();
        }
        return uniqueNumber;
    }
}
