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
public class ClienteResponse {
    
    private String id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private Boolean consentimentoLgpd;
    private LocalDateTime criadoEm;
}
