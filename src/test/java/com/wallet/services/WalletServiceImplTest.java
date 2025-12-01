package com.wallet.services;

import com.wallet.config.EnvironmentService;
import com.wallet.dto.AddWalletRequest;
import com.wallet.models.CurrencyEntity;
import com.wallet.models.ProfileEntity;
import com.wallet.models.UserEntity;
import com.wallet.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
class WalletServiceImplTest{

    private final EnvironmentService environmentService;
    private final WalletRepository walletRepository;
    private final WalletService walletService;


    @BeforeEach
    void setUp() {
        final int amountUsers = 5;
        final int amountProfiles = 5;
        environmentService.initializeProfilesWithUsers(amountProfiles, amountUsers);

        final int amountCurrencies = 3;
        environmentService.initializeCurrencies(amountCurrencies);
    }

    @Test
    void addShouldCreateNewWallet() {
        //given
        final int userId = 0;
        final int currencyId = 0;
        UserEntity user = new UserEntity(userId);
        CurrencyEntity currency = new CurrencyEntity(currencyId);
        AddWalletRequest request = new AddWalletRequest(currency.getId());

        //when
        walletService.add(user.getId(), request);

        //then
        var res = walletRepository.findByProfile(new ProfileEntity(userId));
        assertNotNull(res);
    }

}