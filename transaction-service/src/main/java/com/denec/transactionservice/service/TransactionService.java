package com.denec.transactionservice.service;

import com.denec.transactionservice.kafka.KafkaTransactionProducer;
import com.denec.transactionservice.model.TransactionCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final KafkaTransactionProducer kafkaTransactionProducer;

    public void addTransaction(TransactionCreateRequest request) {
        kafkaTransactionProducer.sendMessage(request);
    }
}
