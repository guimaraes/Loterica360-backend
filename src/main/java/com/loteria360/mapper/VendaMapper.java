package com.loteria360.mapper;

import com.loteria360.domain.dto.VendaBolaoRequest;
import com.loteria360.domain.dto.VendaJogoRequest;
import com.loteria360.domain.dto.VendaResponse;
import com.loteria360.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, JogoMapper.class, BolaoMapper.class, ClienteMapper.class, PagamentoMapper.class})
public interface VendaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turno", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "tipo", constant = "JOGO")
    @Mapping(target = "jogo", source = "jogoId", qualifiedByName = "vendaJogoIdToJogo")
    @Mapping(target = "bolao", ignore = true)
    @Mapping(target = "quantidadeApostas", source = "quantidade")
    @Mapping(target = "cotasVendidas", ignore = true)
    @Mapping(target = "valorBruto", ignore = true)
    @Mapping(target = "desconto", defaultValue = "0.00")
    @Mapping(target = "acrescimo", defaultValue = "0.00")
    @Mapping(target = "valorLiquido", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "motivoCancelamento", ignore = true)
    @Mapping(target = "pagamentos", ignore = true)
    Venda toEntity(VendaJogoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turno", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "tipo", constant = "BOLAO")
    @Mapping(target = "jogo", ignore = true)
    @Mapping(target = "bolao", source = "bolaoId", qualifiedByName = "bolaoIdToBolao")
    @Mapping(target = "quantidadeApostas", ignore = true)
    @Mapping(target = "cotasVendidas", source = "cotas")
    @Mapping(target = "valorBruto", ignore = true)
    @Mapping(target = "desconto", defaultValue = "0.00")
    @Mapping(target = "acrescimo", defaultValue = "0.00")
    @Mapping(target = "valorLiquido", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "motivoCancelamento", ignore = true)
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
