package com.wallet.services.impl;

import com.wallet.dto.request.CurrencyAddRequest;
import com.wallet.dto.response.CurrencyIdResultResponse;
import com.wallet.enums.status.CurrencyResponseStatus;
import com.wallet.models.CurrencyEntity;
import com.wallet.repositories.CurrencyRepository;
import com.wallet.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public CurrencyIdResultResponse add(CurrencyAddRequest request) {
        if (currencyRepository.existsByNameAndCode(request.name(), request.code())) {
            return new CurrencyIdResultResponse(CurrencyResponseStatus.CANCELLED_IS_ALREADY_EXIST);
        }
        final CurrencyEntity currency = request.buildCurrencyEntity();
        return new CurrencyIdResultResponse(
                currencyRepository.save(currency).getId(),
                CurrencyResponseStatus.OK
        );
    }

    @Override
    public boolean isNotExist(int id) {
        return !currencyRepository.existsById(id);
    }
}
