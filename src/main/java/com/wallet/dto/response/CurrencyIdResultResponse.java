package com.wallet.dto.response;

import com.wallet.enums.status.CurrencyResponseStatus;

public record CurrencyIdResultResponse(int id, CurrencyResponseStatus responseStatus) {

    public CurrencyIdResultResponse(CurrencyResponseStatus responseStatus) {
        this(0, responseStatus);
    }
}
