package com.denec.transactionservice.controller;

import com.denec.transactionservice.model.TransactionCancelRequest;
import com.denec.transactionservice.model.TransactionCreateRequest;
import com.denec.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/create")
    void createTransaction(@RequestBody TransactionCreateRequest request) {
        transactionService.createTransaction(request);
    }

    @PostMapping("/cancel")
    void cancelTransaction(@RequestBody TransactionCancelRequest request) {
        transactionService.cancelTransaction(request);
    }
}