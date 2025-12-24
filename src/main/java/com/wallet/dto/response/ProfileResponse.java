package com.wallet.dto.response;

import com.wallet.models.ProfileEntity;
import lombok.Data;


@Data
public class ProfileResponse {
    private final int id;
    private final String fullName;
    private final UserResponse user;

    public ProfileResponse(ProfileEntity p) {
        this.id = p.getId();
        this.fullName = p.getFullName();
        this.user = new UserResponse(p.getUser());
    }
}
