package com.wallet.dto.response;

import com.wallet.models.CurrencyEntity;
import lombok.Data;

@Data
public class CurrencyObjResponse {
    private final int id;
    private final String name;
    private final String code;

    public CurrencyObjResponse(CurrencyEntity c) {
        this.id = c.getId();
        this.name = c.getName();
        this.code = c.getCode();
    }
}
