package com.wallet.dto.response;

import com.wallet.enums.AuthStatus;
import lombok.Data;

@Data
public class UserAuthResponse {

    private int userId;
    private String token;
    private AuthStatus status;

    public UserAuthResponse(int userId, String token, AuthStatus status) {
        this.userId = userId;
        this.token = token;
        this.status = status;
    }

    public UserAuthResponse(AuthStatus status) {
        this.status = status;
    }
}
