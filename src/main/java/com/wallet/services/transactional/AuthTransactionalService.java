package com.wallet.services.transactional;

import com.wallet.dto.response.UserAuthResponse;
import com.wallet.enums.AuthStatus;
import com.wallet.models.UserEntity;
import com.wallet.repositories.UserRepository;
import com.wallet.services.ProfileService;
import com.wallet.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthTransactionalService {

    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final TokenService tokenService;

    @Transactional
    public UserAuthResponse signUp(UserEntity newUser) {
        final int userId = userRepository.save(newUser).getId();
        profileService.create(newUser);
        final String token = tokenService.create(userId).getToken();

        return new UserAuthResponse(userId, token, AuthStatus.OK);
    }
}
