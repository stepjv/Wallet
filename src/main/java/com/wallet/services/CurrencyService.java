package com.wallet.services;

import com.wallet.dto.request.CurrencyAddRequest;
import com.wallet.dto.response.CurrencyIdResultResponse;

public interface CurrencyService {

    /// API
    CurrencyIdResultResponse add(CurrencyAddRequest request);

    /// HELP

    boolean isNotExist(int id);

}
