package com.denec.clientservice.Kafka;

import com.denec.clientservice.model.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaTransactionErrorProducer {
    private final KafkaTemplate<String, TransactionDto> template;

    public void sendMessage(TransactionDto transactionDto) {
        try {
            template.sendDefault(transactionDto).get();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            template.flush();
        }
    }
}
