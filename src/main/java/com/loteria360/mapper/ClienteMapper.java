package com.loteria360.mapper;

import com.loteria360.domain.dto.ClienteRequest;
import com.loteria360.domain.dto.ClienteResponse;
import com.loteria360.domain.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    Cliente toEntity(ClienteRequest request);

    ClienteResponse toResponse(Cliente cliente);
}
