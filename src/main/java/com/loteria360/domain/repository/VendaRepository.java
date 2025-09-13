package com.loteria360.domain.repository;

import com.loteria360.domain.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface VendaRepository extends JpaRepository<Venda, UUID> {
    
    List<Venda> findByTurnoIdOrderByCriadoEmDesc(UUID turnoId);
    
    List<Venda> findByUsuarioIdAndCriadoEmBetweenOrderByCriadoEmDesc(
            UUID usuarioId, LocalDateTime inicio, LocalDateTime fim);
    
    @Query("SELECT v FROM Venda v WHERE v.turno.id = :turnoId AND v.status = 'ATIVA'")
    List<Venda> findVendasAtivasByTurno(@Param("turnoId") UUID turnoId);
    
    @Query("SELECT COALESCE(SUM(v.valorLiquido), 0) FROM Venda v WHERE v.turno.id = :turnoId AND v.status = 'ATIVA'")
    BigDecimal getTotalVendasByTurno(@Param("turnoId") UUID turnoId);
    
    @Query("SELECT COALESCE(SUM(p.valor), 0) FROM Venda v JOIN v.pagamentos p WHERE v.turno.id = :turnoId AND v.status = 'ATIVA' AND p.metodo = :metodo")
    BigDecimal getTotalVendasByTurnoAndMetodo(@Param("turnoId") UUID turnoId, @Param("metodo") String metodo);
}
