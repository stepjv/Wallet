package com.wallet.dto.response;

import com.wallet.enums.status.TransactionResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
public class TransactionListResponse {
    private Page<TransactionResponse> transactionResponseList;
    private TransactionResponseStatus transactionResponseStatus;
}
