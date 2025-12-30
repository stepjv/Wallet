package com.wallet.dto.request;

import com.wallet.models.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public record UserAuthRequest(String email, String password) {
    public UserEntity buildUserEntity() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return UserEntity.buildNewUser(
                email,
                encoder.encode(password)
        );
    }
}
