package com.wallet.services.impl;

import com.wallet.dto.CurrencyAddRequest;
import com.wallet.models.CurrencyEntity;
import com.wallet.repositories.CurrencyRepository;
import com.wallet.services.CurrencyService;
import com.wallet.util.exceptions.IsExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public int add(CurrencyAddRequest request) {
        if (currencyRepository.existsByNameAndCode(request.name(), request.code())) {
            throw new IsExistException("a similar currency already exists");
        }
        final CurrencyEntity currency = request.buildCurrencyEntity();
        return currencyRepository.save(currency).getId();
    }
}
