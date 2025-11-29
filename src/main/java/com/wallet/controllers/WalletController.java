package com.wallet.controllers;

import com.wallet.dto.AddWalletRequest;
import com.wallet.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/add")
    public void newWallet(@RequestBody AddWalletRequest request) {
        walletService.add(request);
    }
}
