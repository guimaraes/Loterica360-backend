package com.loteria360.domain.model;

import lombok.Getter;

@Getter

public enum StatusVenda {
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusVenda(String descricao) {
        this.descricao = descricao;
    }
    
}
