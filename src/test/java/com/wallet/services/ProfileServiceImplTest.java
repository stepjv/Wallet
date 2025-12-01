package com.wallet.services;

import com.wallet.config.EnvironmentService;
import com.wallet.models.UserEntity;
import com.wallet.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
class ProfileServiceImplTest{

    private final EnvironmentService environmentService;
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;

    @BeforeEach
    void setUp() {
        final int amountUsers = 5;
        environmentService.initializeUsers(amountUsers);
    }

    @Test
    void addShouldCreateOneProfile() {
        //when
        final int userId = 0;

        //given
        profileService.add(new UserEntity(userId));

        //then
        var res = profileRepository.findProfileByUser(new UserEntity(userId));
        assertNotNull(res);
    }
}