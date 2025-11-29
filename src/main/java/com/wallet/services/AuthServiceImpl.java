package com.wallet.services;

import com.wallet.dto.SignUpRequest;
import com.wallet.models.UserEntity;
import com.wallet.repositories.UserRepository;
import com.wallet.util.exceptions.IsExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public void signUp(SignUpRequest request) {
        if (userRepository.existUserByEmail(request.email())) {
            throw new IsExistException("User with this email is exist!");
        }
        final UserEntity newUser = request.buildUserEntity();
        newUser.setCreatedAt(Timestamp.valueOf(
                String.valueOf(Instant.now())
        ));
        userRepository.save(newUser);
    }
}