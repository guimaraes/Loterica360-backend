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
public class LoginResponse {
    
    private String token;
    private String tipo;
    private LocalDateTime expiresAt;
    private UsuarioResponse usuario;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsuarioResponse {
        private String id;
        private String nome;
        private String email;
        private PapelUsuario papel;
        private Boolean ativo;
        private LocalDateTime criadoEm;
        private LocalDateTime atualizadoEm;
    }
}
