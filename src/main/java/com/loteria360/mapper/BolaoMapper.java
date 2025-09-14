package com.loteria360.mapper;

import com.loteria360.domain.dto.BolaoResponse;
import com.loteria360.domain.dto.CriarBolaoRequest;
import com.loteria360.domain.model.Bolao;
import com.loteria360.domain.model.Jogo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {JogoMapper.class})
public interface BolaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "jogo", source = "jogoId", qualifiedByName = "jogoIdToJogo")
    @Mapping(target = "cotasVendidas", ignore = true)
    @Mapping(target = "status", ignore = true)
    Bolao toEntity(CriarBolaoRequest request);

    @Mapping(target = "jogo", source = "jogo")
    @Mapping(target = "cotasDisponiveis", source = ".", qualifiedByName = "calculateCotasDisponiveis")
    BolaoResponse toResponse(Bolao bolao);

    @Named("jogoIdToJogo")
    default Jogo jogoIdToJogo(String jogoId) {
        if (jogoId == null) {
            return null;
        }
        Jogo jogo = new Jogo();
        jogo.setId(jogoId);
        return jogo;
    }

    @Named("calculateCotasDisponiveis")
    default Integer calculateCotasDisponiveis(Bolao bolao) {
        if (bolao == null || bolao.getCotasTotais() == null || bolao.getCotasVendidas() == null) {
            return 0;
        }
        return bolao.getCotasTotais() - bolao.getCotasVendidas();
    }
}
