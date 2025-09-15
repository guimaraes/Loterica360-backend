package com.loteria360.repository;

import com.loteria360.domain.model.StatusVenda;
import com.loteria360.domain.model.TipoVenda;
import com.loteria360.domain.model.Venda;
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
public interface VendaRepository extends JpaRepository<Venda, String> {

    @Query("SELECT v FROM Venda v WHERE v.turno.id = :turnoId")
    Page<Venda> findByTurnoId(@Param("turnoId") String turnoId, Pageable pageable);

    @Query("SELECT v FROM Venda v WHERE v.turno.usuario.id = :usuarioId")
    Page<Venda> findByUsuarioId(@Param("usuarioId") String usuarioId, Pageable pageable);

    @Query("SELECT v FROM Venda v WHERE v.status = :status")
    Page<Venda> findByStatus(@Param("status") StatusVenda status, Pageable pageable);

    @Query("SELECT v FROM Venda v WHERE v.tipoVenda = :tipo")
    Page<Venda> findByTipo(@Param("tipo") TipoVenda tipo, Pageable pageable);

    @Query("SELECT v FROM Venda v WHERE v.bolao.id = :bolaoId")
    List<Venda> findByBolaoId(@Param("bolaoId") String bolaoId);

    @Query("SELECT v FROM Venda v WHERE v.dataVenda BETWEEN :dataInicio AND :dataFim")
    Page<Venda> findByDataVendaBetween(@Param("dataInicio") LocalDateTime dataInicio, 
                                      @Param("dataFim") LocalDateTime dataFim, 
                                      Pageable pageable);

    @Query("SELECT v FROM Venda v WHERE v.turno.usuario.id = :usuarioId AND v.dataVenda BETWEEN :dataInicio AND :dataFim")
    Page<Venda> findByUsuarioIdAndDataVendaBetween(@Param("usuarioId") String usuarioId,
                                                   @Param("dataInicio") LocalDateTime dataInicio, 
                                                   @Param("dataFim") LocalDateTime dataFim, 
                                                   Pageable pageable);

    @Query("SELECT COUNT(v) FROM Venda v WHERE v.turno.id = :turnoId AND v.status = 'CONCLUIDA'")
    Long countVendasAtivasByTurnoId(@Param("turnoId") String turnoId);

    @Query("SELECT v FROM Venda v WHERE v.turno.id = :turnoId AND v.status = 'CONCLUIDA'")
    List<Venda> findVendasAtivasByTurnoId(@Param("turnoId") String turnoId);
}
