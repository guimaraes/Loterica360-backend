package com.loteria360.domain.model;

import lombok.Getter;

@Getter

public enum EscopoComissao {
    JOGO("Jogo"),
    BOLAO("Bolão"),
    VENDEDOR("Vendedor");

    private final String descricao;

    EscopoComissao(String descricao) {
        this.descricao = descricao;
    }
    
}
