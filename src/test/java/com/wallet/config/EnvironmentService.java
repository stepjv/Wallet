package com.wallet.config;

import com.wallet.config.entity.*;
import com.wallet.dto.request.CurrencyAddRequest;
import com.wallet.dto.request.TransactionTransferRequest;
import com.wallet.dto.request.UserAuthRequest;
import com.wallet.dto.request.WalletCreateRequest;
import com.wallet.dto.response.TransactionIdResultResponse;
import com.wallet.services.*;
import com.wallet.util.exceptions.IsExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class EnvironmentService {

    private static final String LETTERS_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LETTERS_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String EMAIL_DOMAIN = "@mail.ru";
    private static final int CURRENCY_CODE_LENGTH = 3;
    private static final int CURRENCY_NAME_LENGTH = 5;
    private static final String DESCRIPTION = "description";

    private final AuthService authService;
    private final ProfileService profileService;
    private final CurrencyService currencyService;
    private final WalletService walletService;
    private final TransactionService transactionService;


    public List<UserTestObj> initializeSignUp(int amountUsers) {
        List<UserTestObj> users = new ArrayList<>();

        final int passwordLength = 10;

        for (int i = 0; i < amountUsers; i++) {
            String email = generateEmail();
            String password = getRandomString(LETTERS_LOWERCASE, passwordLength);

            UserAuthRequest request = new UserAuthRequest(
                    email,
                    password
            );
            try {
                int userId = authService.signUp(request).getUserId();
                users.add(UserTestObj.fullBuild(userId, email, password));
            } catch (IsExistException e) {
                i--;
            }
        }
        return users;
    }
    public List<UserTestObj> initializeSignUp(List<UserTestObj> users) {
        for (UserTestObj user : users){
            UserAuthRequest request = new UserAuthRequest(
                    user.getEmail(),
                    user.getPassword()
            );
            int userId = authService.signUp(request).getUserId();
            user.setId(userId);
        }
        return users;
    }

    public List<ProfileTestObj> initializeProfiles(int amountProfiles) {
        List<UserTestObj> users = initializeSignUp(amountProfiles);
        List<ProfileTestObj> profiles = new ArrayList<>();

        for (UserTestObj user : users) {
            int profileId = profileService.getByUserId(user.getId()).getId();
            profiles.add(new ProfileTestObj(profileId, user));
        }

        return profiles;
    }

    public List<CurrencyTestObj> initializeCurrencies(int amountCurrencies) {
        List<CurrencyTestObj> currencies = new ArrayList<>();

        for (int i = 0; i < amountCurrencies; i++) {
            currencies.add(initializeOneCurrency());
        }

        return currencies;
    }

    public CurrencyTestObj initializeOneCurrency() {
        CurrencyTestObj currency;
        while (true) {

            CurrencyAddRequest request = new CurrencyAddRequest(
                    getRandomString(LETTERS_LOWERCASE, CURRENCY_NAME_LENGTH),
                    getRandomString(LETTERS_UPPERCASE, CURRENCY_CODE_LENGTH)
            );
            try {
                int currencyId = currencyService.add(request).id();

                currency = new CurrencyTestObj(currencyId);

                break;
            } catch (IsExistException ignored) {}

        }
        return currency;
    }

    public List<WalletTestObj> initializeWallet(int amountWallets) {
        CurrencyTestObj currency = initializeOneCurrency();
        List<ProfileTestObj> profiles = initializeProfiles(amountWallets);
        List<WalletTestObj> wallets = new ArrayList<>();

        for (ProfileTestObj profile : profiles) {
            int walletId = walletService.create(
                    profile.getUser().getId(),
                    new WalletCreateRequest(currency.getId())
            ).walletId();
            wallets.add(WalletTestObj.buildZeroBalanceWalletByWalletInitializer(walletId, currency, profile));
        }

        return wallets;
    }

    public TransactionIdResultResponse initializeOnePendingTransaction(WalletTestObj walletOut, WalletTestObj walletIn, BigDecimal money) {
        TransactionTransferRequest request = new TransactionTransferRequest(
                walletOut.getId(), walletIn.getId(), money, DESCRIPTION
        );
        return transactionService.sendTransferRequest(walletOut.getProfile().getId(), request);
    }

    /// INTERNAL HELP

    private String generateEmail() {
        final int emailNameLength = 5;
        return getRandomString(LETTERS_LOWERCASE, emailNameLength) + EMAIL_DOMAIN;
    }

    private String getRandomString(String characters, int length) {
        final SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
