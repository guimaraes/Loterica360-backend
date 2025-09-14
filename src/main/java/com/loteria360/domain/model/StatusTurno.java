package com.loteria360.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum StatusTurno {
    ABERTO("Aberto"),
    FECHADO("Fechado");

    private final String descricao;

    StatusTurno(String descricao) {
        this.descricao = descricao;
    }
}
