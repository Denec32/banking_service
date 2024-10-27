package com.denec.clientservice.mapper;

import com.denec.clientservice.model.Client;
import com.denec.clientservice.model.dto.ClientDto;
import com.denec.clientservice.model.request.ClientRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {
    Client toEntity(ClientRegisterRequest request);

    ClientDto toDto(Client client);
}