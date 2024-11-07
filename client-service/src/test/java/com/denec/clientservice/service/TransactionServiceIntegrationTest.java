package com.denec.clientservice.service;

import com.denec.clientservice.Kafka.KafkaTransactionErrorProducer;
import com.denec.clientservice.mapper.TransactionMapper;
import com.denec.clientservice.model.AccountType;
import com.denec.clientservice.model.Transaction;
import com.denec.clientservice.model.dto.AccountDto;
import com.denec.clientservice.model.request.TransactionCreateRequest;
import com.denec.clientservice.repository.TransactionRepository;
import com.denec.clientservice.web.TransactionCheckWebClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
class TransactionServiceIntegrationTest {
    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    AccountService accountService;

    @Spy
    TransactionMapper transactionMapper;

    @Mock
    KafkaTransactionErrorProducer kafkaTransactionErrorProducer;

    @Autowired
    TransactionCheckWebClient transactionCheckWebClient;

    @Autowired
    TransactionService transactionService;

    @Test
    void createTransaction() {
        when(accountService.findById(0L)).thenReturn(
                AccountDto.builder()
                        .accountType(AccountType.DEBIT)
                        .id(0L)
                        .balance(new BigDecimal("100"))
                        .isBlocked(false)
                        .clientId(0L)
                        .build()
        );

        TransactionCreateRequest request = TransactionCreateRequest
                .builder()
                .amount(new BigDecimal("100"))
                .clientId(0L)
                .accountId(0L).build();

        Transaction transaction = new Transaction(0L, new BigDecimal("100"), 0L, 0L);

        doReturn(transaction).when(transactionRepository).save(transaction);

        doReturn(transaction)
                .when(transactionMapper)
                .toEntity(request);

        doNothing().when(kafkaTransactionErrorProducer).sendMessage(any());

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(
                () -> transactionService.createTransaction(request)
        );
    }
}