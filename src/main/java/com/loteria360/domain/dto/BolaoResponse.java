package com.loteria360.domain.dto;

import com.loteria360.domain.model.StatusBolao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BolaoResponse {
    
    private String id;
    private JogoResponse jogo;
    private String concurso;
    private String descricao;
    private Integer cotasTotais;
    private Integer cotasVendidas;
    private Integer cotasDisponiveis;
    private BigDecimal valorCota;
    private LocalDate dataSorteio;
    private StatusBolao status;
}
