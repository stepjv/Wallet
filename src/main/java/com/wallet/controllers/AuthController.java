package com.wallet.controllers;

import com.wallet.dto.request.UserSignUpRequest;
import com.wallet.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup") // return signUpDTO где лежит токен
    public void signUp(@RequestBody UserSignUpRequest request) {
        authService.signUp(request);
    }

    // TODO signIn();
}
