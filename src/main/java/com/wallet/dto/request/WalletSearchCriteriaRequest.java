package com.wallet.dto.request;

import lombok.Data;

@Data
public class WalletSearchCriteriaRequest {
    private int pageNumber;
    private int pageSize;


    public static WalletSearchCriteriaRequest build(int pageNumber, int pageSize) {
        return new WalletSearchCriteriaRequest(pageNumber, pageSize);
    }


    private WalletSearchCriteriaRequest(
            int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}
