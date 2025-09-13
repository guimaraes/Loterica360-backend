package com.loteria360.repository;

import com.loteria360.domain.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, UUID> {
    
    @Query("SELECT t FROM Turno t WHERE t.usuario.id = :usuarioId AND t.status = 'ABERTO'")
    Optional<Turno> findTurnoAbertoByUsuario(@Param("usuarioId") UUID usuarioId);
    
    @Query("SELECT t FROM Turno t WHERE t.caixaId = :caixaId AND t.status = 'ABERTO'")
    Optional<Turno> findTurnoAbertoByCaixa(@Param("caixaId") String caixaId);
    
    List<Turno> findByUsuarioIdOrderByCriadoEmDesc(UUID usuarioId);
}
