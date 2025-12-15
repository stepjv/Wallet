package com.wallet.dto;

import com.wallet.models.CurrencyEntity;
import lombok.Data;

@Data
public class CurrencyDTO {
    private final int id;
    private final String name;
    private final String code;

    public CurrencyDTO(CurrencyEntity c) {
        this.id = c.getId();
        this.name = c.getName();
        this.code = c.getCode();
    }
}
