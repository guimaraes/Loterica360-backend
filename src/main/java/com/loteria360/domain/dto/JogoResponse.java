package com.loteria360.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JogoResponse {
    
    private String id;
    private String nome;
    private String codigo;
    private BigDecimal precoBase;
    private String regrasJson;
    private Boolean ativo;
}
