package com.loteria360.domain.dto;

import com.loteria360.domain.model.PapelUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    
    private String id;
    private String nome;
    private String email;
    private PapelUsuario papel;
    private Boolean ativo;
    private LocalDateTime criadoEm;
}
