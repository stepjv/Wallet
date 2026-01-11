package com.wallet.dto.response;

import com.wallet.enums.status.TransactionResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TransactionListResponse {
    private List<TransactionResponse> transactionResponseList;
    private TransactionResponseStatus transactionResponseStatus;
}
