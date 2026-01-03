package com.wallet.services.impl;

import com.wallet.dto.request.WalletCreateRequest;
import com.wallet.dto.request.WalletSearchCriteriaRequest;
import com.wallet.dto.response.WalletIdResultResponse;
import com.wallet.dto.response.WalletResponse;
import com.wallet.dto.response.WalletListResponse;
import com.wallet.enums.status.WalletResponseStatus;
import com.wallet.models.ProfileEntity;
import com.wallet.models.WalletEntity;
import com.wallet.repositories.WalletRepository;
import com.wallet.services.CurrencyService;
import com.wallet.services.ProfileService;
import com.wallet.services.WalletService;
import com.wallet.util.RandomNumberGenerator;
import com.wallet.util.Validator;
import com.wallet.util.exceptions.BalanceExceededException;
import com.wallet.util.exceptions.NegativeBalanceException;
import com.wallet.util.exceptions.NotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;


// поставить правильно транзакции
// в транзакциях не должно выполняться запросов которых ты не контролируешь в данном сервисе

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ProfileService profileService;
    private final CurrencyService currencyService;

    @Override
    public WalletIdResultResponse create(int userId, WalletCreateRequest request) {
        final String checkNumber = generateUniqueNumber();

        if (currencyService.isExist(request.currencyId())) {
            return new WalletIdResultResponse(WalletResponseStatus.CANCELLED_CURRENCY_IS_NOT_EXIST);
        }

        final UUID uuid = UUID.randomUUID();

        final ProfileEntity profile = profileService.getByUserId(userId);

        final WalletEntity wallet = request.buildWalletEntity(checkNumber, profile, uuid);

        return new WalletIdResultResponse(
                walletRepository.save(wallet).getId(),
                WalletResponseStatus.OK
        );
    }

    @Override
    public WalletResponseStatus changeBalance(int walletId, BigDecimal money) {

        final WalletEntity wallet = walletRepository.findById(walletId);

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
    public WalletListResponse getAllWalletsByProfileId(int profileId, WalletSearchCriteriaRequest request) {

        final Pageable pageable = PageRequest.of(
                request.getPageNumber(),
                request.getPageSize(),
                Sort.by("id").ascending()
        );

        final Page<WalletEntity> wallets = walletRepository.findAllByProfileId(profileId, pageable);

        return getWalletListResponse(wallets);
    }

    @Override
    public WalletResponse getDTOById(int walletId) {
        return WalletResponse.build(getEntityById(walletId));
    }

    @Override
    public WalletResponseStatus canTransfer(int walletId, BigDecimal money) {

        final WalletEntity wallet = walletRepository.findById(walletId);

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
        final WalletEntity wallet = walletRepository.findById(walletId);
        if (wallet == null) {
            throw new NotExistException();
        }
        return wallet;
    }

    @Override
    public boolean isWalletIdNotOwnedByProfileId(int profileId, int walletId) {
        return walletRepository.isWalletNotOwnedByProfile(profileId, walletId);
    }

    /// INTERNAL HELP

    private WalletListResponse getWalletListResponse(Page<WalletEntity> wallets) {
        if (wallets.isEmpty()) {
            return new WalletListResponse(Page.empty());
        }

        return new WalletListResponse(wallets.
                map(WalletResponse::build)
        );
    }

    private String generateUniqueNumber() {
        String uniqueNumber = RandomNumberGenerator.getWalletCheck();
        while (walletRepository.existWithCheck(uniqueNumber)) {
            uniqueNumber = RandomNumberGenerator.getWalletCheck();
        }
        return uniqueNumber;
    }
}
