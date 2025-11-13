package com.wallet.controllers;

import com.wallet.models.User;
import com.wallet.services.RegistrationService;
import com.wallet.util.ErrorResponse;
import com.wallet.util.IsExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;

    @Autowired
    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }



    @PostMapping("/signup")
    public ResponseEntity<?> registration(@RequestBody User user) {
        registrationService.reg(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(IsExistException exception){
        return new ResponseEntity<>(
                new ErrorResponse(exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

}
