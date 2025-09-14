package com.loteria360.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioBoloesStatusResponse {
    
    private Integer totalBoloes;
    private Integer boloesAbertos;
    private Integer boloesEncerrados;
    private Integer boloesCancelados;
    private Integer totalCotas;
    private Integer cotasVendidas;
    private Integer cotasDisponiveis;
    private List<BolaoStatusResponse> boloes;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BolaoStatusResponse {
        private String id;
        private String jogoNome;
        private String concurso;
        private Integer cotasTotais;
        private Integer cotasVendidas;
        private Integer cotasDisponiveis;
        private String status;
        private String percentualVendido;
    }
}
