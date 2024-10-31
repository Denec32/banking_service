package com.denec.clientservice.Kafka;

import com.denec.clientservice.model.request.TransactionCreationRequest;
import com.denec.clientservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaTransactionConsumer {
    private final TransactionService transactionService;

    @KafkaListener(topics = "${kafka.topic.transaction_created}",
            containerFactory = "transactionKafkaListenerContainerFactory")
    public void listen(@Payload List<TransactionCreationRequest> messages, Acknowledgment ack) {
        try {
            messages.forEach(transactionService::createTransaction);
        } catch (Exception e) {
            log.error("Transaction consumer: ошибка записи: {}", e.getMessage());
        } finally {
            ack.acknowledge();
        }
        log.debug("Transaction consumer: записи обработаны");
    }
}
