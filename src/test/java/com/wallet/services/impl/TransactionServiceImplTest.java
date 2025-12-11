package com.wallet.services.impl;

import com.wallet.WalletApplication;
import com.wallet.config.EnvironmentService;
import com.wallet.config.entity.WalletTestObj;
import com.wallet.dto.request.TransactionReplenishmentRequest;
import com.wallet.dto.request.TransactionTransferOutRequest;
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
import java.util.Random;

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

    private List<WalletTestObj> wallets = new ArrayList<>();

    @BeforeEach
    void setUp() {
        wallets = environmentService.initializeWallet(WALLETS_AMOUNT);
    }

    @Test
    void replenishShouldIncreaseWalletBalance() {
        // given
        Random random = new Random();
        final int walletId = random.nextInt(wallets.size()) + 1;
        WalletEntity wallet = walletRepository.findById(walletId);
        BigDecimal walletBalance = wallet.getBalance();

        TransactionReplenishmentRequest request = new TransactionReplenishmentRequest(walletId, TRANSFER_MONEY_COUNT, "text");

        // when
        TransactionResponse res = transactionService.replenish(request);


        // then
        wallet = walletRepository.findById(walletId);
        Optional<TransactionEntity> transaction = transactionRepository.findById(res.transactionId());

        assertTrue(transaction.isPresent());
        assertEquals(wallet.getBalance(), walletBalance.add(TRANSFER_MONEY_COUNT));
    }

    @Test
    void getAllByWalletId() {
    }

    @Test
    void sendTransferOutRequestShouldCreateCancelledTransactionWithArithmeticError() {
        // given
        Random random = new Random();
        final int transferOutWalletId = random.nextInt(wallets.size()) + 1;
        int transferInWalletId;
        do {
            transferInWalletId = random.nextInt(wallets.size()) + 1;
        } while (transferOutWalletId == transferInWalletId);

        TransactionTransferOutRequest request = new TransactionTransferOutRequest(
                transferOutWalletId, transferInWalletId,
                TRANSFER_MONEY_COUNT, "text");

        // when
        TransactionResponse response = transactionService.sendTransferOutRequest(request);

        // then
        Optional<TransactionEntity> res = transactionRepository.findById(response.transactionId());
        assertTrue(res.isPresent());
        assertEquals(TransactionStatus.CANCELLED, res.get().getStatus());

        System.out.println(res.get().toString());
        System.out.println("Response status -> " + response.status());
    }

    @Test
    void sendTransferOutRequestShouldCreatePendingTransaction() {
        // given
        Random random = new Random();
        final int transferOutWalletId = random.nextInt(wallets.size()) + 1;
        int transferInWalletId;
        do {
            transferInWalletId = random.nextInt(wallets.size()) + 1;
        } while (transferOutWalletId == transferInWalletId);

        transactionService.replenish(new TransactionReplenishmentRequest(transferOutWalletId, TRANSFER_MONEY_COUNT, "text"));

        TransactionTransferOutRequest request = new TransactionTransferOutRequest(
                transferOutWalletId, transferInWalletId,
                TRANSFER_MONEY_COUNT, "text");

        // when
        TransactionResponse response = transactionService.sendTransferOutRequest(request);

        // then
        Optional<TransactionEntity> res = transactionRepository.findById(response.transactionId());
        assertTrue(res.isPresent());
        assertEquals(TransactionStatus.PENDING, res.get().getStatus());

        System.out.println(res.get().toString());
        System.out.println("Response status -> " + response.status());
    }
}