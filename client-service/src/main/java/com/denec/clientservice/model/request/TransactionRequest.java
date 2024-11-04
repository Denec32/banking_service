package com.denec.clientservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private Long cancelTransactionId;
    private BigDecimal amount;
    private Long clientId;
    private Long accountId;
    private TransactionType type;
}
