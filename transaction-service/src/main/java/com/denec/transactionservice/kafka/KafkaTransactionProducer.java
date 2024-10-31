package com.denec.transactionservice.kafka;

import com.denec.transactionservice.model.TransactionCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaTransactionProducer {
    private final KafkaTemplate<String, TransactionCreateRequest> template;

    public void sendMessage(TransactionCreateRequest request) {
        try {
            template.sendDefault(request).get();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            template.flush();
        }
    }
}