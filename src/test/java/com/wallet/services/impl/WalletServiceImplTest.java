package com.wallet.services.impl;

import com.wallet.WalletApplication;
import com.wallet.config.EnvironmentService;
import com.wallet.config.entity.CurrencyTestObj;
import com.wallet.config.entity.ProfileTestObj;
import com.wallet.dto.request.WalletCreateRequest;
import com.wallet.models.CurrencyEntity;
import com.wallet.models.WalletEntity;
import com.wallet.repositories.WalletRepository;
import com.wallet.services.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ComponentScan(basePackages = {"com"})
@SpringBootTest(classes = {WalletApplication.class})
@ActiveProfiles("test")
class WalletServiceImplTest {

    @Autowired
    private EnvironmentService environmentService;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletService walletService;

    private final static int PROFILES_AMOUNT = 3;
    private final static int CURRENCIES_AMOUNT = 3;

    private List<ProfileTestObj> profiles;
    private List<CurrencyTestObj> currencies;


    @BeforeEach
    void setUp() {
        profiles = environmentService.initializeProfiles(PROFILES_AMOUNT);

        currencies = environmentService.initializeCurrencies(CURRENCIES_AMOUNT);
    }


    // сначала вызвать регистрацию и только потом вызвать создание кошелька
    @Test
    void createShouldCreateNewWallet() {
        //given
        Random random = new Random();
        final int userId = random.nextInt(profiles.size()) + 1;
        final int currencyId = random.nextInt(currencies.size()) + 1;

        CurrencyEntity currency = new CurrencyEntity(currencyId);
        WalletCreateRequest request = new WalletCreateRequest(currency.getId());

        //when
        walletService.create(userId, request);

        //then
        final WalletEntity res = walletRepository.findByProfileId(userId);
        assertNotNull(res);
    }

}