package com.denec.clientservice.Kafka;

import com.denec.clientservice.mapper.TransactionMapper;
import com.denec.clientservice.model.request.TransactionCancelRequest;
import com.denec.clientservice.model.request.TransactionCreateRequest;
import com.denec.clientservice.model.request.TransactionRequest;
import com.denec.clientservice.model.request.TransactionType;
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
    public void listen(@Payload List<TransactionRequest> messages, Acknowledgment ack) {
        try {
            for (TransactionRequest request : messages) {
                if (request.getType() == TransactionType.CREATE) {
                    var createRequest = new TransactionCreateRequest(
                            request.getAmount(), request.getClientId(), request.getAccountId());

                    transactionService.createTransaction(createRequest);

                } else {
                    var cancelRequest = new TransactionCancelRequest(request.getCancelTransactionId());
                    transactionService.cancelRequest(cancelRequest);
                }
            }
        } catch (Exception e) {
            log.error("Transaction consumer: ошибка записи: {}", e.getMessage());
        } finally {
            ack.acknowledge();
        }
        log.debug("Transaction consumer: записи обработаны");
    }
}
