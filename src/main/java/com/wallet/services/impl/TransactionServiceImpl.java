package com.wallet.services.impl;

import com.wallet.dto.request.*;
import com.wallet.dto.response.TransactionListResponse;
import com.wallet.dto.response.TransactionIdResultResponse;
import com.wallet.dto.response.TransactionResponse;
import com.wallet.enums.TransactionStatus;
import com.wallet.enums.status.TransactionResponseStatus;
import com.wallet.enums.status.WalletResponseStatus;
import com.wallet.models.TransactionEntity;
import com.wallet.repositories.TransactionRepository;
import com.wallet.services.TransactionService;
import com.wallet.services.WalletService;
import com.wallet.util.RandomNumberGenerator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.wallet.enums.status.TransactionResponseStatus.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletService walletService;

    @Lazy
    @Autowired
    private TransactionServiceImpl self;

    @Override
    public TransactionIdResultResponse replenish(int profileId, TransactionReplenishmentRequest request) {

        if (walletService.isWalletIdNotOwnedByProfileId(profileId, request.walletId())) {
            return new TransactionIdResultResponse(TransactionResponseStatus.CANCELLED_PROFILE_NOT_OWN_THIS_WALLET);
        }

        String transactionNumber = generateUniqueNumber();
        TransactionEntity transaction = request.buildTransactionEntity(transactionNumber);

        return self.replenishTrs(transaction);
    }

    @Transactional
    public TransactionIdResultResponse replenishTrs(TransactionEntity transaction) {
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

    @Override
    public TransactionListResponse getTransactionsByFilter(int profileId, TransactionSearchCriteriaRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPageNumber(),
                request.getPageSize(),
                Sort.by("id").ascending()
        );

        if (request.getStatus() != null) {
            return getAllByWalletIdAndStatus(profileId, request, pageable);
        }

        return getAllByWalletId(profileId, request.getWalletId(), pageable);

    }

    @Override
    public TransactionIdResultResponse sendTransferRequest(int profileId, TransactionTransferRequest request) {

        if (walletService.isWalletIdNotOwnedByProfileId(profileId, request.transferOutWalletId())) {
            return new TransactionIdResultResponse(CANCELLED_PROFILE_NOT_OWN_THIS_WALLET);
        }

        final TransactionEntity transaction = request.buildTransactionEntity(generateUniqueNumber());

        final WalletResponseStatus walletResponseStatus = walletService.canTransfer(
                transaction.getSenderWallet().getId(),
                transaction.getTransferMoneyCount().negate()
        );

        final TransactionResponseStatus transactionResponseStatus = walletResponseStatus.getTransactionResponseStatus();

        if (transactionResponseStatus == OK) {
            transaction.setStatus(TransactionStatus.PENDING);
        } else {
            transaction.setStatus(TransactionStatus.CANCELLED);
        }

        int transactionId = transactionRepository.save(transaction).getId();

        return new TransactionIdResultResponse(transactionId, transactionResponseStatus);
    }

    @NonNull
    private TransactionListResponse getTransactionListResponse(List<TransactionEntity> transactions) {
        if (transactions.isEmpty()) {
            return new TransactionListResponse(Collections.emptyList(), OK);
        }

        return new TransactionListResponse(transactions.stream()
                .map(TransactionResponse::build)
                .toList(),
                OK
        );
    }

    @Override
    public TransactionIdResultResponse acceptTransfer(int profileId, TransactionGetByIdRequest request) {
        final Optional<TransactionEntity> transaction = transactionRepository.findById(request.transactionId());
        if (transaction.isEmpty()) {
            return new TransactionIdResultResponse(CANCELLED_TRANSACTION_NOT_EXIST);
        }
        final int payeeWalletId = transaction.get().getPayeeWallet().getId();

        if (walletService.isWalletIdNotOwnedByProfileId(profileId, payeeWalletId)) {
            return new TransactionIdResultResponse(CANCELLED_PROFILE_NOT_OWN_THIS_WALLET);
        }

        return self.acceptTransferTrs(transaction.get());
    }

    @Transactional
    public TransactionIdResultResponse acceptTransferTrs(TransactionEntity transaction) {
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

    /// ITERNAL HELP

    private TransactionListResponse getAllByWalletIdAndStatus(
            int profileId, TransactionSearchCriteriaRequest request, Pageable pageable) {

        if (walletService.isWalletIdNotOwnedByProfileId(profileId, request.getWalletId())) {
            return new TransactionListResponse(Collections.emptyList(), CANCELLED_PROFILE_NOT_OWN_THIS_WALLET);
        }

        List<TransactionEntity> transactions = transactionRepository.findAllByWalletIdAndTransactionStatus(
                request.getWalletId(),
                request.getStatus(),
                pageable
        );

        return getTransactionListResponse(transactions);
    }

    private TransactionListResponse getAllByWalletId(
            int profileId, int walletId, Pageable pageable) {

        if (walletService.isWalletIdNotOwnedByProfileId(profileId, walletId)) {
            return new TransactionListResponse(Collections.emptyList(), CANCELLED_PROFILE_NOT_OWN_THIS_WALLET);
        }

        List<TransactionEntity> transactions = transactionRepository.findAllByWalletId(
                walletId,
                pageable
        );

        return getTransactionListResponse(transactions);
    }

    private String generateUniqueNumber() {
        String uniqueNumber = RandomNumberGenerator.getTransactionNumber();
        while (transactionRepository.existWithNumber(uniqueNumber)) {
            uniqueNumber = RandomNumberGenerator.getTransactionNumber();
        }
        return uniqueNumber;
    }

}
