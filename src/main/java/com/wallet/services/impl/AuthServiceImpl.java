package com.wallet.services.impl;

import com.wallet.dto.request.UserAuthRequest;
import com.wallet.dto.response.UserAuthResponse;
import com.wallet.enums.AuthStatus;
import com.wallet.models.UserEntity;
import com.wallet.repositories.UserRepository;
import com.wallet.services.AuthService;
import com.wallet.services.ProfileService;
import com.wallet.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.wallet.enums.AuthStatus.CANCELLED_EMAIL_IS_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;

    @Lazy
    @Autowired
    private AuthServiceImpl self;

    @Override
    public UserAuthResponse signUp(UserAuthRequest request) {
        if (userRepository.existUserByEmail(request.email())) {
            return new UserAuthResponse(CANCELLED_EMAIL_IS_ALREADY_EXIST);
        }

        final UserEntity newUser = request.buildUserEntity();
        return self.signUpTrs(newUser);
    }


    @Transactional
    public UserAuthResponse signUpTrs(UserEntity newUser) {
        final int userId = userRepository.save(newUser).getId();
        profileService.create(newUser);
        final String token = tokenService.create(userId).getToken();

        return new UserAuthResponse(userId, token, AuthStatus.OK);
    }

    @Override
    public UserAuthResponse signIn(UserAuthRequest request) {

        final Optional<UserEntity> user = userRepository.findByEmail(
                request.email()
        );

        if (user.isEmpty()) {
            return new UserAuthResponse(AuthStatus.CANCELLED_NOT_EXIST);
        }

        if (isNotCorrectPassword(request.password(), user.get().getPassword())) {
            return new UserAuthResponse(AuthStatus.CANCELLED_INCORRECTED_PASSWORD);
        }

        String token = tokenService.create(user.get().getId()).getToken();

        return new UserAuthResponse(user.get().getId(), token, AuthStatus.OK);
    }

    private boolean isNotCorrectPassword(String inputPassword, String realPassword) {
        return !passwordEncoder.matches(inputPassword, realPassword);
    }
}