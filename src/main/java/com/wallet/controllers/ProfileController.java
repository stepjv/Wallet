package com.wallet.controllers;

import com.wallet.dto.AddProfileRequest;
import com.wallet.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/new")
    public void newProfile(@RequestBody AddProfileRequest request) {
        profileService.add(request);
    }
}
