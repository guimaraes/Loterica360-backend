package com.loteria360.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum PapelUsuario {
    ADMIN("Administrador"),
    GERENTE("Gerente"),
    VENDEDOR("Vendedor"),
    AUDITOR("Auditor");

    private final String descricao;

    PapelUsuario(String descricao) {
        this.descricao = descricao;
    }
}
