package com.wallet.services;

import com.wallet.models.ProfileEntity;
import com.wallet.models.UserEntity;

public interface ProfileService {
    /// API

    /// HELP
    int create(UserEntity user);

    ProfileEntity getByUserId(int userId);

    ProfileEntity getById(int id);
}
