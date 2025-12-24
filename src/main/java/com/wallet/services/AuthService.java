package com.wallet.services;

import com.wallet.dto.request.UserSignUpRequest;

public interface AuthService {

    /// API
    int signUp(UserSignUpRequest request);
}
