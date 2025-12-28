package com.wallet.controllers;

import com.wallet.dto.request.TransactionGetByIdRequest;
import com.wallet.dto.request.TransactionGetByWalletIdRequest;
import com.wallet.dto.request.TransactionReplenishmentRequest;
import com.wallet.dto.request.TransactionTransferRequest;
import com.wallet.dto.response.TransactionIdResultResponse;
import com.wallet.dto.response.TransactionListResponse;
import com.wallet.security.AuthUtil;
import com.wallet.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/replenish")
    public TransactionIdResultResponse replenish(
            @RequestBody TransactionReplenishmentRequest request) {

        return transactionService.replenish(AuthUtil.getProfileId(), request);
    }

    @GetMapping("/getAllByWalletId")
    public TransactionListResponse getAllByWalletId(
            @RequestBody TransactionGetByWalletIdRequest request) {

        return transactionService.getAllByWalletId(AuthUtil.getProfileId(), request);
    }

    @PostMapping("/sendTransferRequest")
    public TransactionIdResultResponse sendTransferRequest(
            @RequestBody TransactionTransferRequest request) {

        return transactionService.sendTransferRequest(AuthUtil.getProfileId(), request);
    }

    @GetMapping("/getPendingTransferRequestsByWalletId")
    public TransactionListResponse getPendingTransferRequestsByWalletId(
            @RequestBody TransactionGetByWalletIdRequest request) {

        return transactionService.getPendingTransferRequestsByWalletId(AuthUtil.getProfileId(), request);
    }

    @PostMapping("/acceptTransfer")
    public TransactionIdResultResponse acceptTransfer(
            @RequestBody TransactionGetByIdRequest request) {

        return transactionService.acceptTransfer(AuthUtil.getProfileId(), request);
    }
}
