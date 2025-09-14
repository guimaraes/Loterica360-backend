package com.loteria360.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loteria360.domain.model.AcaoAuditoria;
import com.loteria360.domain.model.Auditoria;
import com.loteria360.domain.model.Usuario;
import com.loteria360.repository.AuditoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditoriaRepository auditoriaRepository;
    private final ObjectMapper objectMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarAuditoria(String tabela, String registroId, AcaoAuditoria acao, 
                                   Object antes, Object depois) {
        try {
            Usuario usuario = getUsuarioAtual();
            if (usuario == null) {
                log.warn("Não foi possível obter usuário atual para auditoria");
                return;
            }

            String antesJson = antes != null ? objectMapper.writeValueAsString(antes) : null;
            String depoisJson = depois != null ? objectMapper.writeValueAsString(depois) : null;

            Auditoria auditoria = Auditoria.builder()
                    .id(UUID.randomUUID().toString())
                    .tabela(tabela)
                    .registroId(registroId)
                    .acao(acao)
                    .antesJson(antesJson)
                    .depoisJson(depoisJson)
                    .usuario(usuario)
                    .criadoEm(LocalDateTime.now())
                    .build();

            auditoriaRepository.save(auditoria);

            log.info("Auditoria registrada: {} - {} - {} - usuário: {}", 
                    acao, tabela, registroId, usuario.getEmail());

        } catch (Exception e) {
            log.error("Erro ao registrar auditoria: {}", e.getMessage(), e);
        }
    }

    private Usuario getUsuarioAtual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Usuario) {
            return (Usuario) authentication.getPrincipal();
        }
        return null;
    }
}
