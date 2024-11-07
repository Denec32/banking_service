package com.denec.clientservice.mapper;

import com.denec.clientservice.model.Transaction;
import com.denec.clientservice.model.dto.TransactionDto;
import com.denec.clientservice.model.request.TransactionCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TransactionMapperImpl.class)
class TransactionMapperTest {

    @Autowired
    TransactionMapper transactionMapper;

    @Test
    void toDto() {
        Transaction transaction = new Transaction();
        transaction.setAccountId(255L);
        transaction.setClientId(46L);
        transaction.setAmount(new BigDecimal(150));
        transaction.setId(1444L);

        TransactionDto transactionDto = TransactionDto.builder()
                .id(1444L)
                .accountId(255L)
                .clientId(46L)
                .amount(new BigDecimal(150)).build();

        assertThat(transactionMapper.toDto(transaction)).isEqualTo(transactionDto);
    }

    @Test
    void toEntity() {
        Transaction transaction = new Transaction();
        transaction.setAccountId(255L);
        transaction.setClientId(46L);
        transaction.setAmount(new BigDecimal(150));
        transaction.setId(1444L);

        TransactionCreateRequest transactionCreateRequest = TransactionCreateRequest.builder()
                .accountId(255L)
                .clientId(46L)
                .amount(new BigDecimal(150)).build();

        assertThat(transactionMapper.toEntity(transactionCreateRequest))
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(transaction);
    }
}