package com.loteria360.repository;

import com.loteria360.domain.model.ContagemCaixa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContagemCaixaRepository extends JpaRepository<ContagemCaixa, String> {
    
    Optional<ContagemCaixa> findByCaixaIdAndDataContagem(String caixaId, LocalDate dataContagem);
    
    List<ContagemCaixa> findByDataContagem(LocalDate dataContagem);
    
    List<ContagemCaixa> findByCaixaId(String caixaId);
    
    @Query("SELECT c FROM ContagemCaixa c WHERE c.dataContagem BETWEEN :dataInicio AND :dataFim")
    Page<ContagemCaixa> findByDataContagemBetween(@Param("dataInicio") LocalDate dataInicio, 
                                                 @Param("dataFim") LocalDate dataFim, 
                                                 Pageable pageable);
    
    @Query("SELECT c FROM ContagemCaixa c WHERE c.caixa.id = :caixaId AND c.dataContagem BETWEEN :dataInicio AND :dataFim")
    List<ContagemCaixa> findByCaixaIdAndDataContagemBetween(@Param("caixaId") String caixaId,
                                                           @Param("dataInicio") LocalDate dataInicio,
                                                           @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT SUM(c.totalGeral) FROM ContagemCaixa c WHERE c.dataContagem = :dataContagem")
    Double sumTotalGeralByDataContagem(@Param("dataContagem") LocalDate dataContagem);
    
    @Query("SELECT SUM(c.totalNotas) FROM ContagemCaixa c WHERE c.dataContagem = :dataContagem")
    Double sumTotalNotasByDataContagem(@Param("dataContagem") LocalDate dataContagem);
    
    @Query("SELECT SUM(c.totalMoedas) FROM ContagemCaixa c WHERE c.dataContagem = :dataContagem")
    Double sumTotalMoedasByDataContagem(@Param("dataContagem") LocalDate dataContagem);
}
