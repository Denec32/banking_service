package com.denec.clientservice.service;

import com.denec.clientservice.Kafka.KafkaTransactionErrorProducer;
import com.denec.clientservice.mapper.TransactionMapper;
import com.denec.clientservice.model.Account;
import com.denec.clientservice.model.Transaction;
import com.denec.clientservice.model.dto.AccountDto;
import com.denec.clientservice.model.request.TransactionCreationRequest;
import com.denec.clientservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;
    private final KafkaTransactionErrorProducer kafkaTransactionErrorProducer;

    @Transactional
    public void createTransaction(TransactionCreationRequest request) {
        Transaction transaction = transactionMapper.toEntity(request);
        AccountDto account = accountService.findById(request.getAccountId());

        if (account.getIsBlocked()) {
            kafkaTransactionErrorProducer.sendMessage(transactionMapper.toDto(transaction));
        } else {
            accountService.updateBalance(account.getId(), account.getBalance().add(request.getAmount()));

            transactionMapper.toDto(transactionRepository.save(transaction));
        }
    }
}