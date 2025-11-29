package com.wallet.services;

import com.wallet.dto.AddWalletRequest;
import com.wallet.models.ProfileEntity;
import com.wallet.models.WalletEntity;
import com.wallet.repositories.WalletRepository;
import com.wallet.temp.TemporaryGetCurrentUserId;
import com.wallet.util.WalletNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ProfileService profileService;

    @Override
    public void add(AddWalletRequest request) {
        String walletNumber = generateUniqueNumber();
        int currentUserId = TemporaryGetCurrentUserId.getCurrentUserId();
        final ProfileEntity profile = profileService.getByUserId(currentUserId);

        final WalletEntity wallet = request.buildWalletEntity(walletNumber, profile);

        walletRepository.save(wallet);
    }


    private String generateUniqueNumber() {
        String uniqueNumber = WalletNumberGenerator.getNumber();
        while (walletRepository.existWalletWithNumber(uniqueNumber)) {
            uniqueNumber = WalletNumberGenerator.getNumber();
        }
        return uniqueNumber;
    }
}
