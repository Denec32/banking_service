package com.denec.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long cancelTransactionId;
    private BigDecimal amount;
    private Long clientId;
    private Long accountId;
    private TransactionType type;
}