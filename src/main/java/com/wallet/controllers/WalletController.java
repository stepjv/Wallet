package com.wallet.controllers;

import com.wallet.dto.request.WalletCreateRequest;
import com.wallet.dto.response.WalletIdResultResponse;
import com.wallet.dto.response.WalletListResponse;
import com.wallet.dto.response.WalletResponse;
import com.wallet.security.AuthUtil;
import com.wallet.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/create")
    public WalletIdResultResponse create(
            @RequestBody WalletCreateRequest request) {

        return walletService.create(AuthUtil.getProfileId(), request);
    }

    @GetMapping("/getAllMyWallets")
    public WalletListResponse getAllMyWallets() {

        return walletService.getAllWalletsByProfileId(AuthUtil.getProfileId());
    }

    @GetMapping("/{walletId}")
    public WalletResponse getById(
            @PathVariable int walletId) {

        return walletService.getDTOById(walletId);
    }
}
