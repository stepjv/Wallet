package com.wallet.services;

import com.wallet.dto.request.UserAuthRequest;
import com.wallet.dto.response.UserAuthResponse;

public interface AuthService {

    /// API
    UserAuthResponse signUp(UserAuthRequest request);

    UserAuthResponse signIn(UserAuthRequest request);
}
