package com.loteria360.mapper;

import com.loteria360.domain.dto.CaixaRequest;
import com.loteria360.domain.dto.CaixaResponse;
import com.loteria360.domain.model.Caixa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CaixaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    Caixa toEntity(CaixaRequest request);

    CaixaResponse toResponse(Caixa caixa);
}
