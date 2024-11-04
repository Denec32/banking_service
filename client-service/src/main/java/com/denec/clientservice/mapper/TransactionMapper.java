package com.denec.clientservice.mapper;

import com.denec.clientservice.model.Transaction;
import com.denec.clientservice.model.dto.TransactionDto;
import com.denec.clientservice.model.request.TransactionCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
    TransactionDto toDto(Transaction transaction);
    Transaction toEntity(TransactionCreateRequest request);
}