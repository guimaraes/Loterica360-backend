package com.loteria360.domain.model;

import lombok.Getter;

@Getter
public enum StatusBolao {
    ABERTO("Aberto"),
    ENCERRADO("Encerrado"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusBolao(String descricao) {
        this.descricao = descricao;
    }
    
}
