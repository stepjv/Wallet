package com.wallet.dto.request;

import com.wallet.enums.TransactionStatus;
import lombok.Data;

@Data
public class TransactionSearchCriteriaRequest {
    private TransactionStatus status;
    private Integer walletId;
    private int pageNumber;
    private int pageSize;


    public static TransactionSearchCriteriaRequest build(
            TransactionStatus status, Integer walletId, int pageNumber, int pageSize) {
        return new TransactionSearchCriteriaRequest(status, walletId, pageNumber, pageSize);
    }


    private TransactionSearchCriteriaRequest(
            TransactionStatus status, Integer walletId, int pageNumber, int pageSize) {
        this.status = status;
        this.walletId = walletId;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}
