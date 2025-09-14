package com.loteria360.domain.model;

import lombok.Getter;

@Getter

public enum StatusVenda {
    ATIVA("Ativa"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusVenda(String descricao) {
        this.descricao = descricao;
    }
    
}
