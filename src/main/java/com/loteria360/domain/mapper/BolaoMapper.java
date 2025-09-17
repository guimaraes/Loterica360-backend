package com.loteria360.domain.mapper;

import com.loteria360.domain.dto.BolaoResponse;
import com.loteria360.domain.dto.JogoResponse;
import com.loteria360.domain.model.Bolao;
import com.loteria360.domain.model.Jogo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BolaoMapper {

    BolaoMapper INSTANCE = Mappers.getMapper(BolaoMapper.class);

    @Mapping(source = "jogo", target = "jogo")
    BolaoResponse toResponse(Bolao bolao);

    default JogoResponse jogoToJogoResponse(Jogo jogo) {
        if (jogo == null) {
            return null;
        }
        
        return JogoResponse.builder()
                .id(jogo.getId())
                .nome(jogo.getNome())
                .descricao(jogo.getDescricao())
                .preco(jogo.getPreco())
                .ativo(jogo.getAtivo())
                .criadoEm(jogo.getCriadoEm())
                .build();
    }
}
