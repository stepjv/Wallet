package com.wallet.services;

import com.wallet.WalletApplication;
import com.wallet.config.EnvironmentService;
import com.wallet.config.entity.UserTestObj;
import com.wallet.dto.request.UserAuthRequest;
import com.wallet.dto.response.UserAuthResponse;
import com.wallet.repositories.ProfileRepository;
import com.wallet.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ComponentScan(basePackages = {"com"})
@SpringBootTest(classes = {WalletApplication.class})
@ActiveProfiles("test")
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
                userRepository.findById(res.getUserId()).get().getEmail()
        );
    }

    @Test
    void logInShouldIdentifyUserAndCreateNewToken() {
        // given
        UserTestObj user = environmentService.initializeSignUp(1).getFirst();
        final UserAuthRequest request = new UserAuthRequest(user.getEmail(), user.getPassword());

        // when
        final UserAuthResponse res = authService.logIn(request);

        // then
        assertEquals(
                userRepository.findUserByToken(res.getToken()).getEmail(),
                user.getEmail()
        );
    }
}
