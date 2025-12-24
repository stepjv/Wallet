package com.wallet.services.impl;

import com.wallet.models.TokenEntity;
import com.wallet.services.TokenService;

public class TokenServiceImpl implements TokenService {

    @Override
    public TokenEntity create() {

        return new TokenEntity();
    }
}
