package com.loteria360.mapper;

import com.loteria360.domain.dto.PagamentoRequest;
import com.loteria360.domain.dto.VendaRequest;
import com.loteria360.domain.dto.VendaResponse;
import com.loteria360.domain.model.Pagamento;
import com.loteria360.domain.model.Venda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VendaMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turno", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "jogo", ignore = true)
    @Mapping(target = "bolao", ignore = true)
    @Mapping(target = "tipo", ignore = true)
    @Mapping(target = "quantidadeApostas", source = "quantidade")
    @Mapping(target = "cotasVendidas", source = "cotas")
    @Mapping(target = "valorBruto", ignore = true)
    @Mapping(target = "desconto", constant = "0")
    @Mapping(target = "acrescimo", constant = "0")
    @Mapping(target = "valorLiquido", ignore = true)
    @Mapping(target = "status", constant = "ATIVA")
    @Mapping(target = "motivoCancelamento", ignore = true)
    @Mapping(target = "pagamentos", source = "pagamentos")
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    Venda toEntity(VendaRequest request);
    
    @Mapping(target = "turnoId", source = "turno.id")
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "jogoId", source = "jogo.id")
    @Mapping(target = "bolaoId", source = "bolao.id")
    VendaResponse toResponse(Venda venda);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "venda", ignore = true)
    @Mapping(target = "status", constant = "APROVADO")
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    Pagamento toPagamentoEntity(PagamentoRequest request);
}
