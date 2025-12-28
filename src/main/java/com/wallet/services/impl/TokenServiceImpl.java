package com.wallet.services.impl;

import com.wallet.models.TokenEntity;
import com.wallet.repositories.TokenRepository;
import com.wallet.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public TokenEntity create(int userId) {
        return tokenRepository.save(generateToken(userId));
    }

    /// ITERNAL HELP

    private TokenEntity generateToken(int userId) {
        return TokenEntity.build(
                userId,
                UUID.randomUUID().toString()
        );
    }
}
