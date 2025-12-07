package com.wallet.services;

import com.wallet.dto.UserSignUpRequest;

public interface AuthService {

    /// API !  нужно тестить в IT
    int signUp(UserSignUpRequest request);
}
