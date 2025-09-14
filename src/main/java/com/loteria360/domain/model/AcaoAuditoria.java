package com.loteria360.domain.model;

import lombok.Getter;

@Getter

public enum AcaoAuditoria {
    INSERT("Inserção"),
    UPDATE("Atualização"),
    DELETE("Exclusão");

    private final String descricao;

    AcaoAuditoria(String descricao) {
        this.descricao = descricao;
    }
    
}
