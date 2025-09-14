package com.loteria360.domain.model;

import lombok.Getter;

@Getter

public enum MetodoPagamento {
    DINHEIRO("Dinheiro"),
    PIX("PIX"),
    CARTAO_DEBITO("Cartão de Débito"),
    CARTAO_CREDITO("Cartão de Crédito");

    private final String descricao;

    MetodoPagamento(String descricao) {
        this.descricao = descricao;
    }
    
}
