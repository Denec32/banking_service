package com.denec.clientservice.model.dto;

import com.denec.clientservice.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long id;
    private BigDecimal balance;
    private AccountType accountType;
    private Long clientId;
    private Boolean isBlocked;
}