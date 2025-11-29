package com.wallet.services;

import com.wallet.dto.AddProfileRequest;
import com.wallet.models.ProfileEntity;

public interface ProfileService {
    void add(AddProfileRequest request);

    ProfileEntity getByUserId(int userId);
}
