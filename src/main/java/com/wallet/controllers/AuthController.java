package com.wallet.controllers;

import com.wallet.dto.request.UserAuthRequest;
import com.wallet.dto.response.UserAuthResponse;
import com.wallet.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/v1/auth/signUp")
    public UserAuthResponse signUp(@RequestBody UserAuthRequest request) {
        return authService.signUp(request);
    }

    @GetMapping("/api/v1/auth/signIn")
    public UserAuthResponse signIn(@RequestBody UserAuthRequest request) {
        return authService.signIn(request);
    }
}
