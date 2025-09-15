package com.loteria360.repository;

import com.loteria360.domain.model.MovimentoCaixa;
import com.loteria360.domain.model.TipoMovimentoCaixa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentoCaixaRepository extends JpaRepository<MovimentoCaixa, String> {

    @Query("SELECT m FROM MovimentoCaixa m WHERE m.turno.id = :turnoId")
    Page<MovimentoCaixa> findByTurnoId(@Param("turnoId") String turnoId, Pageable pageable);

    @Query("SELECT m FROM MovimentoCaixa m WHERE m.tipo = :tipo")
    Page<MovimentoCaixa> findByTipo(@Param("tipo") TipoMovimentoCaixa tipo, Pageable pageable);

    @Query("SELECT m FROM MovimentoCaixa m WHERE m.turno.id = :turnoId AND m.tipo = :tipo")
    List<MovimentoCaixa> findByTurnoIdAndTipo(@Param("turnoId") String turnoId, 
                                             @Param("tipo") TipoMovimentoCaixa tipo);

    @Query("SELECT m FROM MovimentoCaixa m WHERE m.dataMovimento BETWEEN :dataInicio AND :dataFim")
    Page<MovimentoCaixa> findByDataMovimentoBetween(@Param("dataInicio") LocalDateTime dataInicio, 
                                                    @Param("dataFim") LocalDateTime dataFim, 
                                                    Pageable pageable);

    @Query("SELECT m FROM MovimentoCaixa m WHERE m.turno.id = :turnoId AND m.dataMovimento BETWEEN :dataInicio AND :dataFim")
    Page<MovimentoCaixa> findByTurnoIdAndDataMovimentoBetween(@Param("turnoId") String turnoId,
                                                             @Param("dataInicio") LocalDateTime dataInicio, 
                                                             @Param("dataFim") LocalDateTime dataFim, 
                                                             Pageable pageable);
}
