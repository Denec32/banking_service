package com.denec.transactionservice.mapper;

import com.denec.transactionservice.model.Transaction;
import com.denec.transactionservice.model.TransactionCancelRequest;
import com.denec.transactionservice.model.TransactionCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
    @Mapping(target = "type", expression = "java(com.denec.transactionservice.model.TransactionType.CREATE)")
    Transaction toEntity(TransactionCreateRequest request);

    @Mapping(source = "transactionId", target = "cancelTransactionId")
    @Mapping(target = "type", expression = "java(com.denec.transactionservice.model.TransactionType.CANCEL)")
    Transaction toEntity(TransactionCancelRequest request);
}