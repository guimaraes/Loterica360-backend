package com.loteria360.repository;

import com.loteria360.domain.model.VendaCaixa;
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
public interface VendaCaixaRepository extends JpaRepository<VendaCaixa, String> {
    
    List<VendaCaixa> findByCaixaIdAndDataVenda(String caixaId, LocalDate dataVenda);
    
    List<VendaCaixa> findByDataVenda(LocalDate dataVenda);
    
    List<VendaCaixa> findByCaixaId(String caixaId);
    
    Optional<VendaCaixa> findByCaixaIdAndJogoIdAndDataVenda(String caixaId, String jogoId, LocalDate dataVenda);
    
    @Query("SELECT v FROM VendaCaixa v WHERE v.dataVenda BETWEEN :dataInicio AND :dataFim")
    Page<VendaCaixa> findByDataVendaBetween(@Param("dataInicio") LocalDate dataInicio, 
                                           @Param("dataFim") LocalDate dataFim, 
                                           Pageable pageable);
    
    @Query("SELECT v FROM VendaCaixa v WHERE v.caixa.id = :caixaId AND v.dataVenda BETWEEN :dataInicio AND :dataFim")
    List<VendaCaixa> findByCaixaIdAndDataVendaBetween(@Param("caixaId") String caixaId,
                                                     @Param("dataInicio") LocalDate dataInicio,
                                                     @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT SUM(v.quantidade) FROM VendaCaixa v WHERE v.jogo.id = :jogoId AND v.dataVenda = :dataVenda")
    Long sumQuantidadeByJogoIdAndDataVenda(@Param("jogoId") String jogoId, @Param("dataVenda") LocalDate dataVenda);
    
    @Query("SELECT SUM(v.valorTotal) FROM VendaCaixa v WHERE v.dataVenda = :dataVenda")
    Double sumValorTotalByDataVenda(@Param("dataVenda") LocalDate dataVenda);

    @Query("SELECT v FROM VendaCaixa v WHERE v.dataVenda BETWEEN :dataInicio AND :dataFim")
    List<VendaCaixa> findByDataVendaBetween(@Param("dataInicio") LocalDate dataInicio, 
                                           @Param("dataFim") LocalDate dataFim);
}
