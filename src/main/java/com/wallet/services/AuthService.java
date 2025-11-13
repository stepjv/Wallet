package com.wallet.services;

import com.wallet.dto.SignUpRequest;

public interface AuthService {
    void signUp(SignUpRequest request);
}
