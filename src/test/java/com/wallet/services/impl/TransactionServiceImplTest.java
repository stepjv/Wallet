package com.wallet.services.impl;

import com.wallet.WalletApplication;
import com.wallet.config.EnvironmentService;
import com.wallet.config.entity.WalletTestObj;
import com.wallet.dto.request.TransactionReplenishmentRequest;
import com.wallet.dto.request.TransactionTransferRequest;
import com.wallet.dto.response.TransactionResponse;
import com.wallet.enums.TransactionStatus;
import com.wallet.models.TransactionEntity;
import com.wallet.models.WalletEntity;
import com.wallet.repositories.TransactionRepository;
import com.wallet.repositories.WalletRepository;
import com.wallet.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = {"com"})
@SpringBootTest(classes = {WalletApplication.class})
@ActiveProfiles("test")
class TransactionServiceImplTest {

    @Autowired
    private EnvironmentService environmentService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private WalletRepository walletRepository;

    private static final int WALLETS_AMOUNT = 2;
    private static final BigDecimal TRANSFER_MONEY_COUNT = BigDecimal.valueOf(50.32);
    private static final String DESCRIPTION = "text";

    private List<WalletTestObj> wallets = new ArrayList<>();

    @BeforeEach
    void setUp() {
        wallets = environmentService.initializeWallet(WALLETS_AMOUNT);
    }

    @Test
    void replenishShouldIncreaseWalletBalance() {
        // given
        final int walletId = 1;
        WalletEntity wallet = walletRepository.findById(walletId);

        TransactionReplenishmentRequest request = new TransactionReplenishmentRequest(
                walletId, TRANSFER_MONEY_COUNT, DESCRIPTION
        );

        // when
        TransactionResponse res = transactionService.replenish(wallet.getProfile().getId(), request);


        // then
        WalletEntity walletAfterTransaction = walletRepository.findById(walletId);
        Optional<TransactionEntity> transaction = transactionRepository.findById(res.transactionId());

        assertTrue(transaction.isPresent());
        assertEquals(walletAfterTransaction.getBalance(), wallet.getBalance().add(TRANSFER_MONEY_COUNT));
    }

    @Test
    void sendTransferRequestShouldCreateCancelledTransactionWithArithmeticError() {
        // given
        final WalletEntity transferOutWallet = walletRepository.findById(1);
        final int transferInWalletId = 2;

        TransactionTransferRequest request = new TransactionTransferRequest(
                transferOutWallet.getId(), transferInWalletId,
                TRANSFER_MONEY_COUNT, DESCRIPTION
        );

        // when
        TransactionResponse response = transactionService.sendTransferRequest(
                transferOutWallet.getProfile().getId(), request
        );

        // then
        Optional<TransactionEntity> res = transactionRepository.findById(response.transactionId());
        assertTrue(res.isPresent());
        assertEquals(TransactionStatus.CANCELLED, res.get().getStatus());

        System.out.println(res.get().toString());
        System.out.println("Response status -> " + response.status());
    }

    @Test
    void sendTransferRequestShouldCreatePendingTransaction() {
        // given
        final WalletEntity transferOutWallet = walletRepository.findById(1);
        final int transferInWalletId = 2;

        transactionService.replenish(
                transferOutWallet.getProfile().getId(),
                new TransactionReplenishmentRequest(transferOutWallet.getId(), TRANSFER_MONEY_COUNT, DESCRIPTION)
        );

        TransactionTransferRequest request = new TransactionTransferRequest(
                transferOutWallet.getId(), transferInWalletId,
                TRANSFER_MONEY_COUNT, DESCRIPTION);

        // when
        TransactionResponse response = transactionService.sendTransferRequest(
                transferOutWallet.getProfile().getId(), request
        );

        // then
        Optional<TransactionEntity> res = transactionRepository.findById(response.transactionId());
        assertTrue(res.isPresent());
        assertEquals(TransactionStatus.PENDING, res.get().getStatus());

        System.out.println(res.get().toString());
        System.out.println("Response status -> " + response.status());
    }
}