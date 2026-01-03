package com.wallet.services.impl;

import com.wallet.dto.request.UserAuthRequest;
import com.wallet.dto.response.UserAuthResponse;
import com.wallet.enums.AuthStatus;
import com.wallet.models.UserEntity;
import com.wallet.repositories.UserRepository;
import com.wallet.services.AuthService;
import com.wallet.services.TokenService;
import com.wallet.services.transactional.AuthTransactionalService;
import com.wallet.util.exceptions.IsExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthTransactionalService transactionalService; // создал отдельный класс чтобы не было circular dependency


    @Override
    public UserAuthResponse signUp(UserAuthRequest request) {
        if (userRepository.existUserByEmail(request.email())) {
            throw new IsExistException();
        }

        final UserEntity newUser = request.buildUserEntity();
        return transactionalService.signUp(newUser);
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