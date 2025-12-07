package com.wallet.services.impl;

import com.wallet.dto.UserSignUpRequest;
import com.wallet.models.UserEntity;
import com.wallet.repositories.UserRepository;
import com.wallet.services.AuthService;
import com.wallet.services.ProfileService;
import com.wallet.util.exceptions.IsExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ProfileService profileService;

    @Override
    public int signUp(UserSignUpRequest request) {
        if (userRepository.existUserByEmail(request.email())) {
            throw new IsExistException("User with this email is exist!");
        }

        final UserEntity newUser = request.buildUserEntity();

        int userId = userRepository.save(newUser).getId();

        profileService.create(newUser);

        return userId;
    }
}