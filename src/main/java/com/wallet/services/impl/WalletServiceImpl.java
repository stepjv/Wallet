package com.wallet.services.impl;

import com.wallet.dto.WalletCreateRequest;
import com.wallet.models.ProfileEntity;
import com.wallet.models.WalletEntity;
import com.wallet.repositories.WalletRepository;
import com.wallet.services.ProfileService;
import com.wallet.services.WalletService;
import com.wallet.util.RandomNumberGenerator;
import com.wallet.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ProfileService profileService;

    @Override
    public int create(int userId, WalletCreateRequest request) {
        String walletNumber = generateUniqueNumber();

        UUID uuid = UUID.fromString(walletNumber);

        final ProfileEntity profile = profileService.getByUserId(userId);

        final WalletEntity wallet = request.buildWalletEntity(walletNumber, profile, uuid);

        return walletRepository.save(wallet).getId();
    }

    @Override
    public boolean changeBalance(int walletId, BigDecimal countOfMoney) {
        WalletEntity wallet = walletRepository.findById(walletId);

        try {

            BigDecimal newBalance = Validator.safeSumOfDecimal(wallet.getBalance(), countOfMoney);
            wallet.setBalance(newBalance);

        } catch (ArithmeticException e) {
            return false;
        }

        walletRepository.save(wallet);

        return true;
    }

    @Override
    public WalletEntity getWalletByUserId(int userId) {
        ProfileEntity profile = profileService.getByUserId(userId);
        return walletRepository.findByProfileId(profile.getId());
    }


    private String generateUniqueNumber() {
        String uniqueNumber = RandomNumberGenerator.getWalletNumber();
        while (walletRepository.existWithNumber(uniqueNumber)) {
            uniqueNumber = RandomNumberGenerator.getWalletNumber();
        }
        return uniqueNumber;
    }
}
