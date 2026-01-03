package com.wallet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
public class WalletListResponse {
    private Page<WalletResponse> walletResponseList;
}
