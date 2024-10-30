package com.denec.correctionservice.kafka;

import com.denec.correctionservice.model.TransactionError;
import com.denec.correctionservice.service.TransactionService;
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
public class KafkaTransactionErrorConsumer {
    private final TransactionService transactionService;

    @KafkaListener(topics = "${kafka.topic.transaction_error}",
            containerFactory = "transactionErrorKafkaListenerContainerFactory")
    public void listen(@Payload List<TransactionError> messages, Acknowledgment ack) {
        try {
            messages.forEach(transactionService::addTransaction);
        } catch (Exception e) {
            log.error("Transaction error consumer: ошибка записи: {}", e.getMessage());
        } finally {
            ack.acknowledge();
        }
        log.debug("Transaction error consumer: записи обработаны");
    }
}
