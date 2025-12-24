package com.wallet.services.impl;

import com.wallet.WalletApplication;
import com.wallet.dto.request.UserSignUpRequest;
import com.wallet.repositories.ProfileRepository;
import com.wallet.repositories.UserRepository;
import com.wallet.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ComponentScan(basePackages = {"com"})
@SpringBootTest(classes = {WalletApplication.class})
@ActiveProfiles("test")
class AuthServiceImplTest {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    private static final String EMAIL = "user@mail.ru";
    private static final String PASSWORD = "user";

    @Test
    void signUpShouldCreateNewUserAndProfile() {
        // given
        final UserSignUpRequest request = new UserSignUpRequest(EMAIL, PASSWORD);

        // when
        final int userId = authService.signUp(request);

        // then
        assertNotNull(userRepository.findById(userId));
        assertNotNull(profileRepository.findProfileByUserId(userId));
    }
}
