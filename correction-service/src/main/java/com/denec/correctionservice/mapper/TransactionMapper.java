package com.denec.correctionservice.mapper;

import com.denec.correctionservice.model.Transaction;
import com.denec.correctionservice.model.TransactionError;
import com.denec.correctionservice.model.dto.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
    Transaction toEntity(TransactionError transactionError);
    TransactionDto toDto(Transaction transaction);
}