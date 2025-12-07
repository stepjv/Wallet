package com.wallet.config;

import com.wallet.config.entity.CurrencyTestObj;
import com.wallet.config.entity.ProfileTestObj;
import com.wallet.config.entity.UserTestObj;
import com.wallet.dto.CurrencyAddRequest;
import com.wallet.dto.UserSignUpRequest;
import com.wallet.models.UserEntity;
import com.wallet.services.AuthService;
import com.wallet.services.CurrencyService;
import com.wallet.services.ProfileService;
import com.wallet.util.exceptions.IsExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

    private final AuthService authService;
    private final ProfileService profileService;
    private final CurrencyService currencyService;
    private static final int CURRENCY_CODE_LENGTH = 3;

   // public class GenerateTestUsersObj
  // {
     // вернуть список тестовых обьктов 1 пользователя List<UserTestObj>
  // }

    public List<UserTestObj> initializeUsers(int amountUsers) {
        List<UserTestObj> users = new ArrayList<>();

        final int passwordLength = 10;

        for (int i = 0; i < amountUsers; i++) {
            UserSignUpRequest request = new UserSignUpRequest(
                    generateEmail(),
                    getRandomString(LETTERS_LOWERCASE, passwordLength)
            );
            try {
                int userId = authService.signUp(request);
                users.add(new UserTestObj(userId));
            } catch (IsExistException e) {
                i--;
            }
        }
        return users;
    }

    public List<ProfileTestObj> initializeProfiles(int amountProfiles) {
        List<UserTestObj> users = initializeUsers(amountProfiles);
        List<ProfileTestObj> profiles = new ArrayList<>();

        for (UserTestObj user : users) {
            int profileId = profileService.create(new UserEntity(user.getId()));
            profiles.add(new ProfileTestObj(profileId, user));
        }

        return profiles;
    }

    public List<CurrencyTestObj> initializeCurrencies(int amountCurrencies) {
        List<CurrencyTestObj> currencies = new ArrayList<>();

        final int nameLength = 5;

        for (int i = 0; i < amountCurrencies; i++) {
            CurrencyAddRequest request = new CurrencyAddRequest(
                    getRandomString(LETTERS_LOWERCASE, nameLength),
                    getRandomString(LETTERS_UPPERCASE, CURRENCY_CODE_LENGTH)
            );
            try {
                currencyService.add(request);
            } catch (IsExistException e) {
                i--;
            }
        }

        return currencies;
    }

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
