package com.loteria360.audit;

import com.loteria360.domain.model.AcaoAuditoria;
import com.loteria360.domain.model.Auditoria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {

    private final AuditService auditService;

    @AfterReturning(pointcut = "@annotation(auditable)", returning = "result")
    public void auditMethod(JoinPoint joinPoint, Auditable auditable, Object result) {
        try {
            String tabela = auditable.tabela();
            AcaoAuditoria acao = auditable.acao();

            Object entity = null;
            if (joinPoint.getArgs().length > 0) {
                entity = joinPoint.getArgs()[0];
            }

            if (entity != null && result != null) {
                String id = extractId(result);
                if (id != null) {
                    auditService.registrarAuditoria(tabela, id, acao, null, result);
                }
            }
        } catch (Exception e) {
            log.error("Erro na auditoria automática: {}", e.getMessage(), e);
        }
    }

    private String extractId(Object entity) {
        try {
            Method getIdMethod = entity.getClass().getMethod("getId");
            Object id = getIdMethod.invoke(entity);
            return id != null ? id.toString() : null;
        } catch (Exception e) {
            log.warn("Não foi possível extrair ID da entidade: {}", entity.getClass().getSimpleName());
            return null;
        }
    }
}
