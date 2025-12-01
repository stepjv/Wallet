package com.wallet.services;

import com.wallet.models.ProfileEntity;
import com.wallet.models.UserEntity;

public interface ProfileService {
    void add(UserEntity request);

    ProfileEntity getByUserId(int userId);
}
