package com.loteria360.mapper;

import com.loteria360.domain.dto.ContagemCaixaRequest;
import com.loteria360.domain.dto.ContagemCaixaResponse;
import com.loteria360.domain.model.ContagemCaixa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContagemCaixaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caixa", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "totalNotas", ignore = true)
    @Mapping(target = "totalMoedas", ignore = true)
    @Mapping(target = "totalGeral", ignore = true)
    ContagemCaixa toEntity(ContagemCaixaRequest request);

    @Mapping(target = "caixaId", source = "caixa.id")
    @Mapping(target = "numeroCaixa", source = "caixa.numero")
    @Mapping(target = "descricaoCaixa", source = "caixa.descricao")
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "nomeUsuario", source = "usuario.nome")
    ContagemCaixaResponse toResponse(ContagemCaixa contagemCaixa);
}
