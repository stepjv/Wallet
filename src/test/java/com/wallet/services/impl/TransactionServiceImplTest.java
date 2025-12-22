package com.wallet.services.impl;

import com.wallet.WalletApplication;
import com.wallet.config.EnvironmentService;
import com.wallet.config.entity.WalletTestObj;
import com.wallet.dto.request.TransactionGetByIdRequest;
import com.wallet.dto.request.TransactionReplenishmentRequest;
import com.wallet.dto.request.TransactionTransferRequest;
import com.wallet.dto.response.TransactionIdResultResponse;
import com.wallet.enums.TransactionStatus;
import com.wallet.enums.status.TransactionResponseStatus;
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

    private static final int WALLETS_AMOUNT = 5;
    private static final BigDecimal TRANSFER_MONEY_COUNT = BigDecimal.valueOf(50.32);
    private static final String DESCRIPTION = "description";

    private List<WalletTestObj> wallets = new ArrayList<>();

    @BeforeEach
    void setUp() {
        wallets = environmentService.initializeWallet(WALLETS_AMOUNT);
    }

    /// replenish();

    /**
     * Успешное пополнение
     */
    @Test
    void replenishShouldIncreaseWalletBalance() {
        // given
        final int walletId = 1;
        final WalletEntity wallet = walletRepository.findById(walletId);

        final TransactionReplenishmentRequest request = new TransactionReplenishmentRequest(
                walletId, TRANSFER_MONEY_COUNT, DESCRIPTION
        );

        // when
        final TransactionIdResultResponse res = transactionService.replenish(wallet.getProfile().getId(), request);


        // then
        final WalletEntity walletAfterTransaction = walletRepository.findById(walletId);
        final Optional<TransactionEntity> transaction = transactionRepository.findById(res.transactionId());

        assertTrue(transaction.isPresent());
        assertEquals(walletAfterTransaction.getBalance(), wallet.getBalance().add(TRANSFER_MONEY_COUNT));
    }

    /**
     * Пополнения счета другим пользователем (фейк запрос/фейк профиль)
     */
    @Test
    void replenishShouldReturnCancelledStatusWhenProfileNotOwnThisWallet(){
        // given
        final int walletId = 1;
        final WalletEntity wallet = walletRepository.findById(walletId);

        final TransactionReplenishmentRequest request = new TransactionReplenishmentRequest(
                walletId, TRANSFER_MONEY_COUNT, DESCRIPTION
        );

        final int fakeProfileId = environmentService.initializeProfiles(1).getFirst().getId();

        // when
        final TransactionIdResultResponse res = transactionService.replenish(
                fakeProfileId,
                request
        );


        // then
        final WalletEntity walletAfterTransaction = walletRepository.findById(walletId);

        assertSame(TransactionResponseStatus.CANCELLED_PROFILE_NOT_OWN_THIS_WALLET, res.status());

        assertSame(wallet.getBalance(), walletAfterTransaction.getBalance());
    }

    /// sendTransferRequest();

    /**
     * Успешное создание транзакции.
     * Создает запрос на перевод (создает транзакцию со статусом pending)
     */
    @Test
    void sendTransferRequestShouldCreatePendingTransaction() {
        // given
        final WalletEntity transferOutWallet = walletRepository.findById(1);
        final int transferInWalletId = 2;

        transactionService.replenish(
                transferOutWallet.getProfile().getId(),
                new TransactionReplenishmentRequest(transferOutWallet.getId(), TRANSFER_MONEY_COUNT, DESCRIPTION)
        );

        final TransactionTransferRequest request = new TransactionTransferRequest(
                transferOutWallet.getId(), transferInWalletId,
                TRANSFER_MONEY_COUNT, DESCRIPTION);

        // when
        final TransactionIdResultResponse response = transactionService.sendTransferRequest(
                transferOutWallet.getProfile().getId(), request
        );

        // then
        final Optional<TransactionEntity> res = transactionRepository.findById(response.transactionId());

        assertTrue(res.isPresent());
        assertEquals(TransactionStatus.PENDING, res.get().getStatus());

        System.out.println(res.get().toString());
        System.out.println("Response status -> " + response.status());
    }

    /**
     * Создает транзакцию со статусом cancelled из-за недостаточного баланса кошелька отправителя
     */
    @Test
    void sendTransferRequestShouldCreateCancelledTransactionWithArithmeticError() {
        // given
        final WalletEntity transferOutWallet = walletRepository.findById(1);
        final int transferInWalletId = 2;

        final TransactionTransferRequest request = new TransactionTransferRequest(
                transferOutWallet.getId(), transferInWalletId,
                TRANSFER_MONEY_COUNT, DESCRIPTION
        );

        // when
        final TransactionIdResultResponse response = transactionService.sendTransferRequest(
                transferOutWallet.getProfile().getId(), request
        );

        // then
        final Optional<TransactionEntity> res = transactionRepository.findById(response.transactionId());
        assertTrue(res.isPresent());
        assertEquals(TransactionStatus.CANCELLED, res.get().getStatus());

        System.out.println(res.get().toString());
        System.out.println("Response status -> " + response.status());
    }

    /// acceptTransfer();

    /**
     * Успешная транзакция.
     * Меняет статус ранее созданной транзакции (pending -> completed).
     * Меняет баланс кошельков (перевод средств между кошельками)
     */
    @Test
    void acceptTransferShouldChangeTransactionStatusAndChangeBalanceOfWallets() {
        // given
        final WalletTestObj walletOut = wallets.get(1);
        final WalletTestObj walletIn = wallets.get(2);

        walletOut.setBalance(TRANSFER_MONEY_COUNT);

        transactionService.replenish(
                walletOut.getProfile().getId(),
                new TransactionReplenishmentRequest(walletOut.getId(), TRANSFER_MONEY_COUNT, DESCRIPTION)
        );

        final int transactionId = environmentService.initializeOnePendingTransaction(
                walletOut, walletIn, walletOut.getBalance()
        ).transactionId();

        // when
        final TransactionIdResultResponse res = transactionService.acceptTransfer(
                wallets.get(2).getProfile().getId(),
                new TransactionGetByIdRequest(transactionId)
        );

        // then
        assertSame(TransactionResponseStatus.OK, res.status());

        final WalletEntity newWalletOut = walletRepository.findById(walletOut.getId());
        final WalletEntity newWalletIn = walletRepository.findById(walletIn.getId());

        assertEquals(
                walletOut.getBalance().add(walletIn.getBalance()),
                newWalletIn.getBalance()
        );
        assertEquals(
                walletOut.getBalance().add(TRANSFER_MONEY_COUNT.negate()),
                newWalletOut.getBalance()
        );
    }
}