package com.wallet.services.impl;

import com.wallet.dto.request.UserAuthRequest;
import com.wallet.dto.response.UserAuthResponse;
import com.wallet.enums.AuthStatus;
import com.wallet.models.UserEntity;
import com.wallet.repositories.UserRepository;
import com.wallet.services.AuthService;
import com.wallet.services.ProfileService;
import com.wallet.services.TokenService;
import com.wallet.util.exceptions.IsExistException;
import com.wallet.util.exceptions.NotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserAuthResponse signUp(UserAuthRequest request) {

        if (userRepository.existUserByEmail(request.email())) {
            throw new IsExistException();
        }

        System.out.println(request.password());

        final UserEntity newUser = request.buildUserEntity();

        System.out.println(newUser.getPassword());

        int userId = userRepository.save(newUser).getId();

        profileService.create(newUser);

        String token = tokenService.create(userId).getToken();



        return new UserAuthResponse(userId, token, AuthStatus.OK);
    }

    @Override
    public UserAuthResponse logIn(UserAuthRequest request) {

        Optional<UserEntity> user = userRepository.findByEmail(
                request.email()
        );

        if (user.isEmpty()) {
            return new UserAuthResponse(AuthStatus.CANCELLED_NOT_EXIST);
        }

        boolean isCorrectPassword = passwordEncoder.matches(request.password(), user.get().getPassword());
        if (!isCorrectPassword) {
            return new UserAuthResponse(AuthStatus.CANCELLED_INCORRECTED_PASSWORD);
        }

        String token = tokenService.create(user.get().getId()).getToken();

        return new UserAuthResponse(user.get().getId(), token, AuthStatus.OK);
    }
}