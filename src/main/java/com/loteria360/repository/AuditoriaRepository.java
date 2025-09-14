package com.loteria360.repository;

import com.loteria360.domain.model.Auditoria;
import com.loteria360.domain.model.AcaoAuditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, String> {

    @Query("SELECT a FROM Auditoria a WHERE a.tabela = :tabela")
    Page<Auditoria> findByTabela(@Param("tabela") String tabela, Pageable pageable);

    @Query("SELECT a FROM Auditoria a WHERE a.registroId = :registroId")
    List<Auditoria> findByRegistroId(@Param("registroId") String registroId);

    @Query("SELECT a FROM Auditoria a WHERE a.acao = :acao")
    Page<Auditoria> findByAcao(@Param("acao") AcaoAuditoria acao, Pageable pageable);

    @Query("SELECT a FROM Auditoria a WHERE a.usuario.id = :usuarioId")
    Page<Auditoria> findByUsuarioId(@Param("usuarioId") String usuarioId, Pageable pageable);

    @Query("SELECT a FROM Auditoria a WHERE a.criadoEm BETWEEN :dataInicio AND :dataFim")
    Page<Auditoria> findByCriadoEmBetween(@Param("dataInicio") LocalDateTime dataInicio, 
                                         @Param("dataFim") LocalDateTime dataFim, 
                                         Pageable pageable);

    @Query("SELECT a FROM Auditoria a WHERE a.tabela = :tabela AND a.registroId = :registroId ORDER BY a.criadoEm DESC")
    List<Auditoria> findByTabelaAndRegistroIdOrderByCriadoEmDesc(@Param("tabela") String tabela, 
                                                               @Param("registroId") String registroId);
}
