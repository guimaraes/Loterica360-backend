package com.loteria360.repository;

import com.loteria360.domain.model.ComissaoRegra;
import com.loteria360.domain.model.EscopoComissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComissaoRegraRepository extends JpaRepository<ComissaoRegra, String> {

    @Query("SELECT c FROM ComissaoRegra c WHERE c.escopo = :escopo AND c.referenciaId = :referenciaId AND c.vigenteDe <= :data AND (c.vigenteAte IS NULL OR c.vigenteAte >= :data)")
    Optional<ComissaoRegra> findVigenteByEscopoAndReferencia(@Param("escopo") EscopoComissao escopo, 
                                                            @Param("referenciaId") String referenciaId, 
                                                            @Param("data") LocalDate data);

    @Query("SELECT c FROM ComissaoRegra c WHERE c.escopo = :escopo AND c.vigenteDe <= :data AND (c.vigenteAte IS NULL OR c.vigenteAte >= :data)")
    List<ComissaoRegra> findVigentesByEscopo(@Param("escopo") EscopoComissao escopo, 
                                           @Param("data") LocalDate data);

    @Query("SELECT c FROM ComissaoRegra c WHERE c.vigenteDe <= :data AND (c.vigenteAte IS NULL OR c.vigenteAte >= :data)")
    List<ComissaoRegra> findTodasVigentes(@Param("data") LocalDate data);
}
