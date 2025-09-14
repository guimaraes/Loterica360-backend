package com.loteria360.domain.model;

import lombok.Getter;

@Getter

public enum TipoVenda {
    JOGO("Jogo Individual"),
    BOLAO("Cota de Bolão");

    private final String descricao;

    TipoVenda(String descricao) {
        this.descricao = descricao;
    }
    
}
