package com.loteria360.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaixaResponse {

    private String id;
    private Integer numero;
    private String descricao;
    private Boolean ativo;
    private LocalDateTime criadoEm;
}
