package com.loteria360.mapper;

import com.loteria360.domain.dto.VendaBolaoRequest;
import com.loteria360.domain.dto.VendaJogoRequest;
import com.loteria360.domain.dto.VendaResponse;
import com.loteria360.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {TurnoMapper.class, JogoMapper.class, BolaoMapper.class, ClienteMapper.class, PagamentoMapper.class})
public interface VendaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turno", ignore = true)
    @Mapping(target = "jogo", source = "jogoId", qualifiedByName = "vendaJogoIdToJogo")
    @Mapping(target = "bolao", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "tipoVenda", constant = "JOGO_INDIVIDUAL")
    @Mapping(target = "valorTotal", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dataVenda", ignore = true)
    @Mapping(target = "numerosJogados", source = "numeros")
    @Mapping(target = "cotasCompradas", ignore = true)
    @Mapping(target = "pagamentos", ignore = true)
    Venda toEntity(VendaJogoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turno", ignore = true)
    @Mapping(target = "jogo", ignore = true)
    @Mapping(target = "bolao", source = "bolaoId", qualifiedByName = "bolaoIdToBolao")
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "tipoVenda", constant = "BOLAO")
    @Mapping(target = "valorTotal", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dataVenda", ignore = true)
    @Mapping(target = "numerosJogados", ignore = true)
    @Mapping(target = "cotasCompradas", source = "cotas")
    @Mapping(target = "pagamentos", ignore = true)
    Venda toEntity(VendaBolaoRequest request);

    VendaResponse toResponse(Venda venda);

    @Named("vendaJogoIdToJogo")
    default Jogo vendaJogoIdToJogo(String jogoId) {
        if (jogoId == null) {
            return null;
        }
        Jogo jogo = new Jogo();
        jogo.setId(jogoId);
        return jogo;
    }

    @Named("bolaoIdToBolao")
    default Bolao bolaoIdToBolao(String bolaoId) {
        if (bolaoId == null) {
            return null;
        }
        Bolao bolao = new Bolao();
        bolao.setId(bolaoId);
        return bolao;
    }
}