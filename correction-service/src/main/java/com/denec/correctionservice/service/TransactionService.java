package com.denec.correctionservice.service;

import com.denec.correctionservice.kafka.KafkaTransactionProducer;
import com.denec.correctionservice.mapper.TransactionMapper;
import com.denec.correctionservice.model.Transaction;
import com.denec.correctionservice.model.TransactionError;
import com.denec.correctionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final KafkaTransactionProducer kafkaTransactionProducer;

    public void addTransaction(TransactionError transactionError) {
        RestClient restClient = RestClient.create();

        boolean isAccountUnblocked = Boolean.TRUE.equals(restClient.put()
                .uri("http://localhost:8080/account/{id}/unblock", transactionError.getAccountId())
                .retrieve()
                .body(Boolean.class));

        if (isAccountUnblocked) {
            Transaction transaction = transactionMapper.toEntity(transactionError);
            kafkaTransactionProducer.sendMessage(transactionMapper.toDto(transaction));
        } else {
            transactionRepository.save(transactionMapper.toEntity(transactionError));
        }
    }

    public void resendTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        for (Transaction transaction : transactions) {
            kafkaTransactionProducer.sendMessage(transactionMapper.toDto(transaction));
            //transactionRepository.delete(transaction);
        }
    }
}
