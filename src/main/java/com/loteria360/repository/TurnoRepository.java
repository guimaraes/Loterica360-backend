package com.loteria360.repository;

import com.loteria360.domain.model.StatusTurno;
import com.loteria360.domain.model.Turno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, String> {

    @Query("SELECT t FROM Turno t WHERE t.usuario.id = :usuarioId")
    Page<Turno> findByUsuarioId(@Param("usuarioId") String usuarioId, Pageable pageable);

    @Query("SELECT t FROM Turno t WHERE t.status = :status")
    Page<Turno> findByStatus(@Param("status") StatusTurno status, Pageable pageable);

    @Query("SELECT t FROM Turno t WHERE t.usuario.id = :usuarioId AND t.status = :status")
    Optional<Turno> findByUsuarioIdAndStatus(@Param("usuarioId") String usuarioId, 
                                           @Param("status") StatusTurno status);

    @Query("SELECT t FROM Turno t WHERE t.caixaId = :caixaId AND t.status = :status")
    Optional<Turno> findByCaixaIdAndStatus(@Param("caixaId") String caixaId, 
                                         @Param("status") StatusTurno status);

    @Query("SELECT t FROM Turno t WHERE t.dataAbertura BETWEEN :dataInicio AND :dataFim")
    Page<Turno> findByDataAberturaBetween(@Param("dataInicio") LocalDateTime dataInicio, 
                                         @Param("dataFim") LocalDateTime dataFim, 
                                         Pageable pageable);

    @Query("SELECT t FROM Turno t WHERE t.usuario.id = :usuarioId AND t.dataAbertura BETWEEN :dataInicio AND :dataFim")
    Page<Turno> findByUsuarioIdAndDataAberturaBetween(@Param("usuarioId") String usuarioId,
                                                     @Param("dataInicio") LocalDateTime dataInicio, 
                                                     @Param("dataFim") LocalDateTime dataFim, 
                                                     Pageable pageable);
}
