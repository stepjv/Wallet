package com.wallet.controllers;

import com.wallet.dto.request.UserAuthRequest;
import com.wallet.dto.response.UserAuthResponse;
import com.wallet.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public UserAuthResponse signUp(@RequestBody UserAuthRequest request) {
        return authService.signUp(request);
    }

    @GetMapping("/login")
    public UserAuthResponse logIn(@RequestBody UserAuthRequest request) {
        return authService.logIn(request);
    }
}
