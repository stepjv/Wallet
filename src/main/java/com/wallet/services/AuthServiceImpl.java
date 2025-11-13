package com.wallet.services;

import com.wallet.dto.SignUpRequest;
import com.wallet.models.UserEntity;
import com.wallet.repositories.UserRepository;
import com.wallet.util.IsExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;


    private boolean existUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)).isPresent();
    }

    @Override
    public void signUp(SignUpRequest request) {
        if (existUserByEmail(request.email())) {
            throw new IsExistException("User with this email is exist!");
        }
        final UserEntity newUser = request.buildUserEntity();
        userRepository.save(newUser);
    }
}


