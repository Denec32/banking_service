package com.denec.clientservice.mapper;

import com.denec.clientservice.model.Account;
import com.denec.clientservice.model.dto.AccountDto;
import com.denec.clientservice.model.request.CreditAccountRegisterRequest;
import com.denec.clientservice.model.request.DebitAccountRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {
    @Mapping(target = "accountType", constant = "CREDIT")
    @Mapping(target = "isBlocked", constant = "false")
    @Mapping(source = "balance", target = "balance", defaultValue = "0")
    Account toEntity(CreditAccountRegisterRequest request);

    @Mapping(target = "accountType", constant = "DEBIT")
    @Mapping(target = "isBlocked", constant = "false")
    @Mapping(target = "balance", constant = "0")
    Account toEntity(DebitAccountRegisterRequest request);

    AccountDto toDto(Account account);
}