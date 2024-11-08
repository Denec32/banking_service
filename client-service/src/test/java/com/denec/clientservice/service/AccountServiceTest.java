package com.denec.clientservice.service;

import com.denec.clientservice.exceptions.UnblockDeniedException;
import com.denec.clientservice.mapper.AccountMapper;
import com.denec.clientservice.model.Account;
import com.denec.clientservice.model.AccountType;
import com.denec.clientservice.model.Transaction;
import com.denec.clientservice.model.dto.AccountDto;
import com.denec.clientservice.repository.AccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
    private static AccountService setUpAccountService(Account account) {
        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        AccountMapper accountMapper = Mockito.mock(AccountMapper.class);

        Mockito.when(accountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(Mockito.any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Mockito.when(accountMapper.toDto(account)).thenAnswer(invocation -> {
                    Account acc = invocation.getArgument(0);
                    return AccountDto.builder()
                            .id(acc.getId())
                            .isBlocked(acc.getIsBlocked())
                            .balance(acc.getBalance())
                            .clientId(acc.getClientId())
                            .build();
                }
        );

        return new AccountService(accountRepository, accountMapper);
    }


    private static Account createBaseAccount(AccountType accountType, boolean blocked) {
        return createBaseAccount(accountType, blocked, BigDecimal.ZERO);
    }

    private static Account createBaseAccount(AccountType accountType, boolean blocked, BigDecimal balance) {
        Account account = new Account();
        account.setId(0L);
        account.setClientId(0L);
        account.setIsBlocked(blocked);
        account.setBalance(balance);
        account.setAccountType(accountType);
        return account;
    }

    @Test
    void blockDebitAccount_blocks_debit_account() {
        Account unblockedDebitAccount = createBaseAccount(AccountType.DEBIT, false);

        var accountService = setUpAccountService(unblockedDebitAccount);

        assertThat(accountService.blockDebitAccount(unblockedDebitAccount.getId()).getIsBlocked())
                .isEqualTo(true);
    }

    @Test
    void blockDebitAccount_throws_if_credit_account_passed() {
        Account unblockedCreditAccount = createBaseAccount(AccountType.CREDIT, false);
        assertThatExceptionOfType(UnblockDeniedException.class).isThrownBy(
                () -> setUpAccountService(unblockedCreditAccount).blockDebitAccount(unblockedCreditAccount.getId())
        );
    }

    @Test
    void unblockAccount_unblocks_debit_account() {
        Account blockedDebitAccount = createBaseAccount(AccountType.DEBIT, true);

        var accountService = setUpAccountService(blockedDebitAccount);

        assertThat(accountService.unblockAccount(blockedDebitAccount.getId()).getIsBlocked())
                .isEqualTo(false);
    }

    @Test
    void unblockAccount_unblocks_credit_account_with_positive_balance() {
        Account blockedPositiveCreditAccount = createBaseAccount(AccountType.CREDIT, true, BigDecimal.ONE);

        var accountService = setUpAccountService(blockedPositiveCreditAccount);

        assertThat(accountService.unblockAccount(blockedPositiveCreditAccount.getId()).getIsBlocked())
                .isEqualTo(false);
    }

    @Test
    void unblockAccount_throws_if_negative_balance_credit_account_passed() {
        Account blockedNegativeCreditAccount = createBaseAccount(AccountType.CREDIT, true, new BigDecimal(-1));

        var accountService = setUpAccountService(blockedNegativeCreditAccount);

        assertThatExceptionOfType(UnblockDeniedException.class).isThrownBy(
                () -> accountService.unblockAccount(blockedNegativeCreditAccount.getId())
        );
    }
}