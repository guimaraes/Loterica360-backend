package com.loteria360.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioVendasResponse {
    
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String vendedorId;
    private String vendedorNome;
    private BigDecimal totalVendas;
    private BigDecimal totalComissoes;
    private Integer totalVendasCount;
    private Integer totalVendasCanceladas;
    private List<VendaResumoResponse> vendas;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VendaResumoResponse {
        private String id;
        private String tipo;
        private BigDecimal valorLiquido;
        private String status;
        private String criadoEm;
    }
}
