package com.wallet.services;

import com.wallet.WalletApplication;
import com.wallet.config.EnvironmentService;
import com.wallet.config.entity.UserTestObj;
import com.wallet.dto.request.UserAuthRequest;
import com.wallet.dto.response.UserAuthResponse;
import com.wallet.enums.AuthStatus;
import com.wallet.repositories.ProfileRepository;
import com.wallet.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = {"com"})
@SpringBootTest(classes = {WalletApplication.class})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthServiceTest {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EnvironmentService environmentService;

    private static final String EMAIL = "user@mail.ru";
    private static final String PASSWORD = "user";

    /// signUp();

    @Test
    void signUpShouldCreateNewUserAndProfileAndToken() {
        // given
        final UserAuthRequest request = new UserAuthRequest(EMAIL, PASSWORD);

        // when
        final UserAuthResponse res = authService.signUp(request);

        // then
        assertNotNull(userRepository.findById(res.getUserId()));
        assertNotNull(profileRepository.findProfileByUserId(res.getUserId()));

        assertEquals(
                userRepository.findUserByToken(res.getToken()).getEmail(),
                userRepository.findById(res.getUserId()).orElseThrow().getEmail()
        );
    }

    /**
     * Тест регистрации с существующим email
     */
    @Test
    void signUpShouldReturnCancelledEmailIsAlreadyExist() {
        // given
        final int nullableId = 0;
        final UserAuthRequest request = new UserAuthRequest(EMAIL, PASSWORD);

        // when
        final UserAuthResponse firstResponse = authService.signUp(request);
        final UserAuthResponse secondResponse = authService.signUp(request);

        // then
        assertEquals(
                AuthStatus.OK,
                firstResponse.getStatus()
        );
        assertEquals(
                AuthStatus.CANCELLED_EMAIL_IS_ALREADY_EXIST,
                secondResponse.getStatus()
        );
        assertEquals(
                nullableId,
                secondResponse.getUserId()
        );

    }

    /// signIn();

    @Test
    void signInShouldIdentifyUserAndCreateNewToken() {
        // given
        UserTestObj user = environmentService.initializeSignUp(1).getFirst();
        final UserAuthRequest request = new UserAuthRequest(user.getEmail(), user.getPassword());

        // when
        final UserAuthResponse res = authService.signIn(request);

        // then
        assertEquals(
                userRepository.findUserByToken(res.getToken()).getEmail(),
                user.getEmail()
        );
    }

    /**
     * Тест с несуществующим пользователем
     */
    @Test
    void signInShouldReturnCancelledIsNotExist() {
        // given
        final List<UserTestObj> userTestObjList = List.of(UserTestObj.build(EMAIL, PASSWORD));
        final UserTestObj correctUser = environmentService.initializeSignUp(userTestObjList).getFirst();

        final String incorrectEmail = "admin@mail.ru";
        final UserAuthRequest request = new UserAuthRequest(incorrectEmail, PASSWORD);

        // when
        final UserAuthResponse res = authService.signIn(request);

        // then
        assertNotEquals(incorrectEmail, correctUser.getEmail());
        assertEquals(AuthStatus.CANCELLED_NOT_EXIST, res.getStatus());
        assertNull(res.getToken());
    }

    /**
     * Тест с неправильным паролем
     */
    @Test
    void signInShouldReturnCancelledIncorrectedPassword() {
        // given
        final List<UserTestObj> userTestObjList = List.of(UserTestObj.build(EMAIL, PASSWORD));
        final UserTestObj correctUser = environmentService.initializeSignUp(userTestObjList).getFirst();

        final String incorrectPassword = environmentService.generateIncorrectPassword(PASSWORD);
        final UserAuthRequest request = new UserAuthRequest(EMAIL, incorrectPassword);

        // when
        final UserAuthResponse res = authService.signIn(request);

        // then
        assertNotEquals(incorrectPassword, correctUser.getPassword());
        assertEquals(AuthStatus.CANCELLED_INCORRECTED_PASSWORD, res.getStatus());
        assertNull(res.getToken());
    }


}
