package com.wallet.services;

import com.wallet.WalletApplication;
import com.wallet.config.EnvironmentService;
import com.wallet.dto.request.WalletCreateRequest;
import com.wallet.dto.response.WalletIdResultResponse;
import com.wallet.enums.status.WalletResponseStatus;
import com.wallet.models.WalletEntity;
import com.wallet.repositories.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = {"com"})
@SpringBootTest(classes = {WalletApplication.class})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WalletServiceTest {

    @Autowired
    private EnvironmentService environmentService;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletService walletService;

    private final static int PROFILES_AMOUNT = 3;
    private final static int CURRENCIES_AMOUNT = 3;

    @BeforeEach
    void setUp() {
        environmentService.initializeProfiles(PROFILES_AMOUNT);
        environmentService.initializeCurrencies(CURRENCIES_AMOUNT);
    }

    /// create();

    @Test
    void createShouldCreateNewWallet() {
        //given
        final int userId = 1;
        final int currencyId = 1;

        final WalletCreateRequest request = new WalletCreateRequest(currencyId);

        //when
        WalletIdResultResponse res = walletService.create(userId, request);

        //then
        final WalletEntity newWallet = walletRepository.findByUserId(userId);
        assertNotNull(newWallet);
        assertEquals(WalletResponseStatus.OK, res.status());
    }

    /**
     * Тест с несуществующей валютой
     */
    @Test
    void createShouldReturnCancelledCurrencyIsNotExist() {
        //given
        final int userId = 1;
        final int incorrectCurrencyId = 99999;

        final WalletCreateRequest request = new WalletCreateRequest(incorrectCurrencyId);

        //when
        WalletIdResultResponse res = walletService.create(userId, request);

        //then
        final WalletEntity newWallet = walletRepository.findByUserId(userId);
        assertNull(newWallet);
        assertEquals(WalletResponseStatus.CANCELLED_CURRENCY_IS_NOT_EXIST, res.status());
    }
}