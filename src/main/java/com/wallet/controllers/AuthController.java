package com.wallet.controllers;

import com.wallet.dto.SignUpRequest;
import com.wallet.services.AuthService;
import com.wallet.util.ErrorResponse;
import com.wallet.util.IsExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpRequest request) {
        authService.signUp(request);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(IsExistException exception){
        return new ResponseEntity<>(
                new ErrorResponse(exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

}
