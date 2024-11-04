package com.denec.clientservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCreateRequest {
    private BigDecimal amount;
    private Long clientId;
    private Long accountId;
}