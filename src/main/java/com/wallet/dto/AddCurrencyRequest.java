package com.wallet.dto;

import com.wallet.models.CurrencyEntity;

public record AddCurrencyRequest(String name, String code) {
    public CurrencyEntity buildCurrencyEntity() {
        return CurrencyEntity.builder()
                .name(this.name)
                .code(this.code)
                .build();
    }
}
