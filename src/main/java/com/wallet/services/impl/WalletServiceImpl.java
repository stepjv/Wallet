package com.wallet.services.impl;

import com.wallet.dto.request.WalletCreateRequest;
import com.wallet.dto.response.WalletResponse;
import com.wallet.dto.response.WalletListResponse;
import com.wallet.enums.status.WalletResponseStatus;
import com.wallet.models.ProfileEntity;
import com.wallet.models.WalletEntity;
import com.wallet.repositories.WalletRepository;
import com.wallet.services.ProfileService;
import com.wallet.services.WalletService;
import com.wallet.util.RandomNumberGenerator;
import com.wallet.util.Validator;
import com.wallet.util.exceptions.BalanceExceededException;
import com.wallet.util.exceptions.NegativeBalanceException;
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
    public int create(int profileId, WalletCreateRequest request) {
        String checkNumber = generateUniqueNumber();

        UUID uuid = UUID.randomUUID();

        final ProfileEntity profile = profileService.getById(profileId);

        final WalletEntity wallet = request.buildWalletEntity(checkNumber, profile, uuid);

        return walletRepository.save(wallet).getId();
    }

    @Override
    public WalletResponseStatus changeBalance(int walletId, BigDecimal money) {

        WalletEntity wallet = walletRepository.findById(walletId);

        try {

            BigDecimal newBalance = Validator.safeSumOfDecimal(wallet.getBalance(), money);

            wallet.setBalance(newBalance);
            walletRepository.save(wallet);

        } catch (BalanceExceededException e) {
            return WalletResponseStatus.CANCELLED_BALANCE_EXCEEDED;
        } catch (NegativeBalanceException e) {
            return WalletResponseStatus.CANCELLED_NEGATIVE_BALANCE;
        } catch (Exception e) {
            return WalletResponseStatus.CANCELLED_DATA_BASE_ERROR;
        }

        return WalletResponseStatus.OK;
    }

    @Override
    public WalletListResponse getAllWalletsByUserId(int userId) {
        ProfileEntity profile = profileService.getByUserId(userId);
        List<WalletEntity> wallets = walletRepository.findAllByProfile(profile);
        List<WalletResponse> walletResponseList = new ArrayList<>();

        if (wallets.isEmpty()) {
            return (WalletListResponse) Collections.emptyList();
        }

        for (WalletEntity wallet : wallets) {
            walletResponseList.add(new WalletResponse(wallet));
        }

        return new WalletListResponse(walletResponseList);
    }

    @Override
    public WalletResponse getDTOById(int walletId) {
        return new WalletResponse(walletRepository.findById(walletId));
    }

    @Override
    public WalletResponseStatus canTransfer(int walletId, BigDecimal money) {

        WalletEntity wallet = walletRepository.findById(walletId);

        try {

            Validator.safeSumOfDecimal(wallet.getBalance(), money);

        } catch (BalanceExceededException e) {
            return WalletResponseStatus.CANCELLED_BALANCE_EXCEEDED;
        } catch (NegativeBalanceException e) {
            return WalletResponseStatus.CANCELLED_NEGATIVE_BALANCE;
        }

        return WalletResponseStatus.OK;
    }

    @Override
    public WalletEntity getEntityById(int walletId) {
        return walletRepository.findById(walletId);
    }

    @Override
    public boolean isWalletIdNotOwnedByProfileId(int profileId, int walletId) {
        return walletRepository.isWalletNotOwnedByProfile(profileId, walletId);
    }

    /// INTERNAL HELP

    private String generateUniqueNumber() {
        String uniqueNumber = RandomNumberGenerator.getWalletCheck();
        while (walletRepository.existWithCheck(uniqueNumber)) {
            uniqueNumber = RandomNumberGenerator.getWalletCheck();
        }
        return uniqueNumber;
    }
}
