package com.wallet.controllers;

import com.wallet.dto.request.CurrencyAddRequest;
import com.wallet.dto.response.CurrencyIdResultResponse;
import com.wallet.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping("/api/v1/currency/action/add")
    public CurrencyIdResultResponse add(@RequestBody CurrencyAddRequest request) {
        return currencyService.add(request);
    }
}
