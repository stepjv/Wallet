package com.wallet.dto.response;

import com.wallet.models.ProfileEntity;
import lombok.Data;


@Data
public class ProfileObjResponse {
    private final int id;
    private final String fullName;
    private final UserObjResponse user;

    public ProfileObjResponse(ProfileEntity p) {
        this.id = p.getId();
        this.fullName = p.getFullName();
        this.user = new UserObjResponse(p.getUser());
    }
}
