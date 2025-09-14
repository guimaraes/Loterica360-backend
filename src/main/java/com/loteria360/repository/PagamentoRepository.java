package com.loteria360.repository;

import com.loteria360.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, String> {

    List<Pagamento> findByVendaId(String vendaId);

    @Query("SELECT p FROM Pagamento p WHERE p.venda.turno.id = :turnoId")
    List<Pagamento> findByVendaTurnoId(@Param("turnoId") String turnoId);

    @Query("SELECT p FROM Pagamento p WHERE p.nsu = :nsu AND p.nsu IS NOT NULL")
    List<Pagamento> findByNsu(@Param("nsu") String nsu);

    @Query("SELECT p FROM Pagamento p WHERE p.tid = :tid AND p.tid IS NOT NULL")
    List<Pagamento> findByTid(@Param("tid") String tid);

    @Query("SELECT p FROM Pagamento p WHERE p.venda.id = :vendaId AND p.status = 'APROVADO'")
    List<Pagamento> findAprovadosByVendaId(@Param("vendaId") String vendaId);
}
