package com.loteria360.mapper;

import com.loteria360.domain.dto.VendaCaixaResponse;
import com.loteria360.domain.model.VendaCaixa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VendaCaixaMapper {

    @Mapping(target = "caixaId", source = "caixa.id")
    @Mapping(target = "numeroCaixa", source = "caixa.numero")
    @Mapping(target = "descricaoCaixa", source = "caixa.descricao")
    @Mapping(target = "jogoId", source = "jogo.id")
    @Mapping(target = "nomeJogo", source = "jogo.nome")
    @Mapping(target = "precoJogo", source = "jogo.preco")
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "nomeUsuario", source = "usuario.nome")
    VendaCaixaResponse toResponse(VendaCaixa vendaCaixa);
}
