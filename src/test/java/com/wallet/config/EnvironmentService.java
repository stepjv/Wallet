package com.wallet.config;

import com.wallet.dto.AddCurrencyRequest;
import com.wallet.dto.SignUpRequest;
import com.wallet.models.UserEntity;
import com.wallet.services.AuthService;
import com.wallet.services.CurrencyService;
import com.wallet.services.ProfileService;
import com.wallet.util.exceptions.IsExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

@Configuration
@RequiredArgsConstructor
public class EnvironmentService {

    private static final String LETTERS_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LETTERS_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private final AuthService authService;
    private final ProfileService profileService;
    private final CurrencyService currencyService;
    private static final int CURRENCY_CODE_LENGTH = 3;

    public void initializeUsers(int amountUsers) {
        final int passwordLength = 10;
        for (int i = 0; i < amountUsers; i++) {
            SignUpRequest request = new SignUpRequest(
                    generateEmail(),
                    getRandomString(LETTERS_LOWERCASE, passwordLength)
            );
            try {
                authService.signUp(request);
            } catch (IsExistException e) {
                i--;
            }

        }
    }

    public void initializeProfilesWithUsers(int amountProfiles, int amountUsers) {
        initializeUsers(amountUsers);

        if (amountProfiles > amountUsers) {
            amountProfiles = amountUsers;
        }

        for (int userId = 0; userId < amountProfiles; userId++) {
            profileService.add(new UserEntity(userId));
        }
    }

    public void initializeCurrencies(int amountCurrencies) {
        final int nameLength = 5;
        for (int i = 0; i < amountCurrencies; i++) {
            AddCurrencyRequest request = new AddCurrencyRequest(
                    getRandomString(LETTERS_LOWERCASE, nameLength),
                    getRandomString(LETTERS_UPPERCASE, CURRENCY_CODE_LENGTH)
            );
            try {
                currencyService.add(request);
            } catch (IsExistException e) {
                i--;
            }

        }
    }

    private String generateEmail() {
        final int emailNameLength = 5;
        return getRandomString(LETTERS_LOWERCASE, emailNameLength) +
                "@mail.ru";
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
