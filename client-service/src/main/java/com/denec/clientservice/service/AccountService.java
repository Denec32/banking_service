package com.denec.clientservice.service;

import com.denec.clientservice.mapper.AccountMapper;
import com.denec.clientservice.model.Account;
import com.denec.clientservice.model.AccountType;
import com.denec.clientservice.model.dto.AccountDto;
import com.denec.clientservice.model.request.CreditAccountRegisterRequest;
import com.denec.clientservice.model.request.DebitAccountRegisterRequest;
import com.denec.clientservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountDto registerDebitAccount(DebitAccountRegisterRequest request) {
        Account account = accountMapper.toEntity(request);
        return accountMapper.toDto(accountRepository.save(account));
    }

    public AccountDto registerCreditAccount(CreditAccountRegisterRequest request) {
        Account account = accountMapper.toEntity(request);
        return accountMapper.toDto(accountRepository.save(account));
    }

    public AccountDto blockDebitAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        if (account.getAccountType() == AccountType.CREDIT) throw new RuntimeException("can't block credit account");

        account.setIsBlocked(true);
        return accountMapper.toDto(accountRepository.save(account));
    }
}