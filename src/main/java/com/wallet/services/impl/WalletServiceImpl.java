package com.wallet.services.impl;

import com.wallet.dto.request.WalletCreateRequest;
import com.wallet.dto.WalletDTO;
import com.wallet.enums.RequestStatus;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ProfileService profileService;

    @Override
    public int create(int userId, WalletCreateRequest request) {
        String checkNumber = generateUniqueNumber();

        UUID uuid = UUID.randomUUID();

        final ProfileEntity profile = profileService.getByUserId(userId);

        final WalletEntity wallet = request.buildWalletEntity(checkNumber, profile, uuid);

        return walletRepository.save(wallet).getId();
    }

    @Override
    public RequestStatus canTransfer(int walletId, BigDecimal countOfMoney, boolean transferable) {
        WalletEntity wallet = walletRepository.findById(walletId);

        try {

            BigDecimal newBalance = Validator.safeSumOfDecimal(wallet.getBalance(), countOfMoney);
            if (transferable) {
                wallet.setBalance(newBalance);
                walletRepository.save(wallet);
            }

        } catch (ArithmeticException e) {
            return RequestStatus.ARITHMETIC_ERROR;
        } catch (Exception e) {
            return RequestStatus.BD_ERROR;
        }

        return RequestStatus.OK;
    }

    @Override
    public List<WalletDTO> getAllWalletsByUserId(int userId) {
        ProfileEntity profile = profileService.getByUserId(userId);
        List<WalletEntity> wallets = walletRepository.findAllByProfile(profile);
        List<WalletDTO> walletDTOList = new ArrayList<>();

        if (wallets.isEmpty()) {
            return Collections.emptyList();
        }

        for (WalletEntity wallet : wallets) {
            walletDTOList.add(new WalletDTO(wallet));
        }

        return walletDTOList;
    }


    private String generateUniqueNumber() {
        String uniqueNumber = RandomNumberGenerator.getWalletCheck();
        while (walletRepository.existWithCheck(uniqueNumber)) {
            uniqueNumber = RandomNumberGenerator.getWalletCheck();
        }
        return uniqueNumber;
    }
}
