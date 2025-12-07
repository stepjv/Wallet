package com.wallet.services.impl;

import com.wallet.models.ProfileEntity;
import com.wallet.models.UserEntity;
import com.wallet.repositories.ProfileRepository;
import com.wallet.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;


    @Override
    public int create(UserEntity user) {
        final ProfileEntity newProfile = user.buildProfileEntity();
        return profileRepository.save(newProfile).getId();
    }

    @Override
    public ProfileEntity getByUserId(int userId) {
        return profileRepository.findProfileByUserId(userId);
    }
}
