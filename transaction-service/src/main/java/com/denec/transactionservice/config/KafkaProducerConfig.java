package com.denec.transactionservice.config;

import com.denec.transactionservice.kafka.KafkaTransactionProducer;
import com.denec.transactionservice.model.Transaction;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.topic.transaction_created}")
    private String transactionTopic;

    @Bean
    @ConditionalOnProperty(value = "kafka.producer.enable", havingValue = "true", matchIfMissing = true)
    public KafkaTransactionProducer kafkaTransactionProducer() {
        Map<String, Object> props = producerConfigs();
        var producerFactory = new DefaultKafkaProducerFactory<String, Transaction>(props);
        var template = new KafkaTemplate<>(producerFactory);
        template.setDefaultTopic(transactionTopic);

        return new KafkaTransactionProducer(template);
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);

        return props;
    }
}