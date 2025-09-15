package com.loteria360.mapper;

import com.loteria360.domain.dto.PagamentoRequest;
import com.loteria360.domain.dto.PagamentoResponse;
import com.loteria360.domain.model.Pagamento;
import com.loteria360.domain.model.Venda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "venda", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dataPagamento", ignore = true)
    @Mapping(source = "metodo", target = "metodoPagamento")
    Pagamento toEntity(PagamentoRequest request);

    PagamentoResponse toResponse(Pagamento pagamento);
}
