package com.loteria360.mapper;

import com.loteria360.domain.dto.CriarJogoRequest;
import com.loteria360.domain.dto.JogoResponse;
import com.loteria360.domain.model.Jogo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JogoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    Jogo toEntity(CriarJogoRequest request);

    JogoResponse toResponse(Jogo jogo);
}
