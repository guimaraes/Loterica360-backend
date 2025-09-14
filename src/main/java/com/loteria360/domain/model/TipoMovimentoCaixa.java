package com.loteria360.domain.model;

import lombok.Getter;

@Getter

public enum TipoMovimentoCaixa {
    SANGRIA("Sangria"),
    SUPRIMENTO("Suprimento");

    private final String descricao;

    TipoMovimentoCaixa(String descricao) {
        this.descricao = descricao;
    }
    
}
