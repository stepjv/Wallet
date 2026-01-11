package com.wallet.controllers;

import com.wallet.dto.request.WalletCreateRequest;
import com.wallet.dto.request.WalletSearchCriteriaRequest;
import com.wallet.dto.response.WalletIdResultResponse;
import com.wallet.dto.response.WalletListResponse;
import com.wallet.dto.response.WalletResponse;
import com.wallet.security.AuthUtil;
import com.wallet.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/api/v1/wallet/create")
    public WalletIdResultResponse create(@RequestBody WalletCreateRequest request) {

        return walletService.create(AuthUtil.getProfileId(), request);
    }

    @GetMapping("/api/v1/wallet/myWallets")
    public WalletListResponse getAllMyWallets(
            @RequestParam(name = "page", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(name = "size", defaultValue = "10", required = false) int pageSize) {

        WalletSearchCriteriaRequest request = WalletSearchCriteriaRequest.build(pageNumber, pageSize);
        return walletService.getAllWalletsByProfileId(AuthUtil.getProfileId(), request);
    }

    @GetMapping("/api/v1/wallet/{walletId}")
    public WalletResponse getById(@PathVariable int walletId) {

        return walletService.getDTOById(walletId);
    }
}
