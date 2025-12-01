package com.wallet.services;

import com.wallet.dto.NewProfileDTO;
import com.wallet.models.ProfileEntity;
import com.wallet.models.UserEntity;
import com.wallet.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;


    @Override
    public void add(UserEntity user) {
        final NewProfileDTO newProfileDTO = new NewProfileDTO();
        final ProfileEntity newProfile = newProfileDTO.buildProfileEntity(user);
        profileRepository.save(newProfile);
    }

    @Override
    public ProfileEntity getByUserId(int userId) {
        UserEntity user = new UserEntity(userId);
        return profileRepository.findProfileByUser(user);
    }
}
