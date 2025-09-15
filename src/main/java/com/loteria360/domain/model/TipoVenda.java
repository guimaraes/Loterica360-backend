package com.loteria360.domain.model;

import lombok.Getter;

@Getter

public enum TipoVenda {
    JOGO_INDIVIDUAL("Jogo Individual"),
    BOLAO("Cota de Bolão");

    private final String descricao;

    TipoVenda(String descricao) {
        this.descricao = descricao;
    }
    
}
