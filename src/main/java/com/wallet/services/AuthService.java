package com.wallet.services;

import com.wallet.dto.request.UserSignUpRequest;

public interface AuthService {

    /// API !  нужно тестить в IT
    int signUp(UserSignUpRequest request);
}
