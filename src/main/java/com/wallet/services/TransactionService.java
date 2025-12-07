package com.wallet.services;

import com.wallet.dto.TransactionReplenishmentRequest;

public interface TransactionService {

    /// API
    int replenish(TransactionReplenishmentRequest request);

    /// HELP

}
