package com.wallet.dto.response;

import com.wallet.dto.WalletDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WalletListResponse {
    private List<WalletDTO> walletDTOList;
}
