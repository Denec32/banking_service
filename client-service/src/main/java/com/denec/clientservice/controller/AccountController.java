package com.denec.clientservice.controller;

import com.denec.clientservice.model.dto.AccountDto;
import com.denec.clientservice.model.request.CreditAccountRegisterRequest;
import com.denec.clientservice.model.request.DebitAccountRegisterRequest;
import com.denec.clientservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/debit")
    public AccountDto registerDebitAccount(@RequestBody DebitAccountRegisterRequest request) {
        return accountService.registerDebitAccount(request);
    }

    @PostMapping("/credit")
    public AccountDto registerCreditAccount(@RequestBody CreditAccountRegisterRequest request) {
        return accountService.registerCreditAccount(request);
    }

    @PutMapping("/{id}/block")
    public AccountDto blockDebitAccount(@PathVariable Long id) {
        return accountService.blockDebitAccount(id);
    }

    @PutMapping("/{id}/unblock")
    public AccountDto unblockAccount(@PathVariable Long id) {
        return accountService.blockDebitAccount(id);
    }
}