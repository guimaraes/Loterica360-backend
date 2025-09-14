package com.loteria360.mapper;

import com.loteria360.domain.dto.MovimentoCaixaRequest;
import com.loteria360.domain.dto.MovimentoCaixaResponse;
import com.loteria360.domain.model.MovimentoCaixa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovimentoCaixaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turno", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    MovimentoCaixa toEntity(MovimentoCaixaRequest request);

    MovimentoCaixaResponse toResponse(MovimentoCaixa movimento);
}
