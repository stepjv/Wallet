package com.wallet.services;

import com.wallet.dto.AddCurrencyRequest;
import com.wallet.models.CurrencyEntity;
import com.wallet.repositories.CurrencyRepository;
import com.wallet.util.exceptions.IsExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public void add(AddCurrencyRequest request) {
        if (currencyRepository.existsByNameAndCode(request.name(), request.code())) {
            throw new IsExistException("a similar currency already exists");
        }
        final CurrencyEntity currency = request.buildCurrencyEntity();
        currencyRepository.save(currency);
    }
}
