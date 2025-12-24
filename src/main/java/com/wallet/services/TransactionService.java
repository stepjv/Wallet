package com.wallet.services;

import com.wallet.dto.request.TransactionGetByIdRequest;
import com.wallet.dto.request.TransactionGetByWalletIdRequest;
import com.wallet.dto.request.TransactionReplenishmentRequest;
import com.wallet.dto.request.TransactionTransferRequest;
import com.wallet.dto.response.TransactionListResponse;
import com.wallet.dto.response.TransactionIdResultResponse;

public interface TransactionService {

    /// API
    TransactionIdResultResponse replenish(int profileId, TransactionReplenishmentRequest request);

    TransactionListResponse getAllByWalletId(int profileId, TransactionGetByWalletIdRequest request);

    TransactionIdResultResponse sendTransferRequest(int profileId, TransactionTransferRequest request);

    TransactionListResponse getPendingTransferRequestsByWalletId(int profileId, TransactionGetByWalletIdRequest request);

    TransactionIdResultResponse acceptTransfer(int profileId, TransactionGetByIdRequest request);

    /// HELP

}
