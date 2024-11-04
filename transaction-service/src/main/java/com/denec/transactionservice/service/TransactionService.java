package com.denec.transactionservice.service;

import com.denec.transactionservice.kafka.KafkaTransactionProducer;
import com.denec.transactionservice.mapper.TransactionMapper;
import com.denec.transactionservice.model.TransactionCancelRequest;
import com.denec.transactionservice.model.TransactionCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final KafkaTransactionProducer kafkaTransactionProducer;
    private final TransactionMapper transactionMapper;

    public void createTransaction(TransactionCreateRequest request) {
        kafkaTransactionProducer.sendMessage(transactionMapper.toEntity(request));
    }

    public void cancelTransaction(TransactionCancelRequest request) {
        kafkaTransactionProducer.sendMessage(transactionMapper.toEntity(request));
    }
}