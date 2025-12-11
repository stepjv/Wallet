package com.wallet.services;

import com.wallet.dto.request.TransactionReplenishmentRequest;
import com.wallet.dto.request.TransactionTransferOutRequest;
import com.wallet.dto.response.TransactionResponse;
import com.wallet.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {

    /// API
    TransactionResponse replenish(TransactionReplenishmentRequest request);

    List<TransactionDTO> getAllByWalletId(int walletId);

    TransactionResponse sendTransferOutRequest(TransactionTransferOutRequest request);

    int acceptTransferOutRequest();

    /// HELP

}
