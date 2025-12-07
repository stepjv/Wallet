package com.wallet.services.impl;

import com.wallet.config.EnvironmentService;
import com.wallet.config.entity.CurrencyTestObj;
import com.wallet.config.entity.ProfileTestObj;
import com.wallet.dto.WalletCreateRequest;
import com.wallet.models.CurrencyEntity;
import com.wallet.models.WalletEntity;
import com.wallet.repositories.WalletRepository;
import com.wallet.services.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
class WalletServiceImplTest {

    private final EnvironmentService environmentService;
    private final WalletRepository walletRepository;
    private final WalletService walletService;

    private final static int PROFILES_AMOUNT = 5;
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
        final int userId = random.nextInt(profiles.size());
        final int currencyId = random.nextInt(currencies.size());

        CurrencyEntity currency = new CurrencyEntity(currencyId);
        WalletCreateRequest request = new WalletCreateRequest(currency.getId());

        //when
        walletService.create(userId, request);

        //then
        final WalletEntity res = walletRepository.findByProfileId(userId);
        assertNotNull(res);
    }

}