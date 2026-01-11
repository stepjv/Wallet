package com.wallet.services;

import com.wallet.dto.request.*;
import com.wallet.dto.response.TransactionListResponse;
import com.wallet.dto.response.TransactionIdResultResponse;

public interface TransactionService {

    /// API
    TransactionIdResultResponse replenish(int profileId, TransactionReplenishmentRequest request);

    TransactionListResponse getTransactionsByFilter(int profileId, TransactionSearchCriteriaRequest request);

    TransactionIdResultResponse sendTransferRequest(int profileId, TransactionTransferRequest request);

    TransactionIdResultResponse acceptTransfer(int profileId, TransactionGetByIdRequest request);

    /// HELP

}
