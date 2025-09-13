package com.loteria360.domain.dto;

import com.loteria360.domain.model.Usuario;
import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nome,
        String email,
        Usuario.Papel papel,
        Boolean ativo,
        LocalDateTime criadoEm
) {}
