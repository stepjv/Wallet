package com.wallet.dto;

import com.wallet.models.ProfileEntity;
import lombok.Data;


@Data
public class ProfileDTO {
    private final int id;
    private final String fullName;
    private final UserDTO user;

    public ProfileDTO(ProfileEntity p) {
        this.id = p.getId();
        this.fullName = p.getFullName();
        this.user = new UserDTO(p.getUser());
    }
}
