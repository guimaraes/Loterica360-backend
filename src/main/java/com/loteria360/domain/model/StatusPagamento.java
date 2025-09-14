package com.loteria360.domain.model;

import lombok.Getter;

@Getter

public enum StatusPagamento {
    APROVADO("Aprovado"),
    PENDENTE("Pendente"),
    ESTORNADO("Estornado");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }
    
}
