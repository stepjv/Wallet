package com.wallet.controllers;

import com.wallet.dto.request.*;
import com.wallet.dto.response.TransactionIdResultResponse;
import com.wallet.dto.response.TransactionListResponse;
import com.wallet.enums.TransactionStatus;
import com.wallet.security.AuthUtil;
import com.wallet.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/api/v1/transaction/replenish")
    public TransactionIdResultResponse replenish(
            @RequestBody TransactionReplenishmentRequest request) {

        return transactionService.replenish(AuthUtil.getProfileId(), request);
    }


    @PostMapping("/api/v1/transaction/transferRequest")
    public TransactionIdResultResponse sendTransferRequest(
            @RequestBody TransactionTransferRequest request) {

        return transactionService.sendTransferRequest(AuthUtil.getProfileId(), request);
    }

    @PostMapping("/api/v1/transaction/acceptTransfer")
    public TransactionIdResultResponse acceptTransfer(
            @RequestBody TransactionGetByIdRequest request) {

        return transactionService.acceptTransfer(AuthUtil.getProfileId(), request);
    }


    // по rest у Get Delete нету тела запроса могут быть только параметры в строке url ,pathVariable reqParam
    // это должна быть 1 апи с выбором татуса в req передаешь например Pending


    @GetMapping("/api/v1/transactions")
    public TransactionListResponse getTransactions(
            @RequestParam(name = "status", required = false) String statusStr,
            @RequestParam(name = "walletId", required = false) Integer walletId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(name = "size", defaultValue = "10", required = false) int pageSize
    ) {

        TransactionStatus status = TransactionStatus.fromString(statusStr);

        TransactionSearchCriteriaRequest request = TransactionSearchCriteriaRequest.build(
                status, walletId, pageNumber, pageSize);

        return transactionService.getTransactionsByFilter(AuthUtil.getProfileId(), request);
    }


}
