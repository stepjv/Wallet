package com.wallet.services.impl;

import com.wallet.dto.UserSignUpRequest;
import com.wallet.repositories.ProfileRepository;
import com.wallet.repositories.UserRepository;
import com.wallet.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
class AuthServiceImplTest {

    private final ProfileRepository profileRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    private static final String EMAIL = "user@mail.ru";
    private static final String PASSWORD = "user";

    @Test
    void signUpShouldCreateNewUserAndProfile() {
        // when
        UserSignUpRequest request = new UserSignUpRequest(EMAIL, PASSWORD);

        // given
        int userId = authService.signUp(request);

        // then
        assertNotNull(userRepository.findById(userId));
        assertNotNull(profileRepository.findProfileByUserId(userId));
    }
}