package com.loteria360.audit;

import com.loteria360.domain.model.AcaoAuditoria;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    String tabela();
    AcaoAuditoria acao();
}
