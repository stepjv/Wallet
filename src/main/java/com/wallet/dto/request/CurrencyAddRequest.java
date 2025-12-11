package com.wallet.dto.request;

import com.wallet.models.CurrencyEntity;

public record CurrencyAddRequest(String name, String code) {
    public CurrencyEntity buildCurrencyEntity() {
        return new CurrencyEntity(name, code);
    }
}
