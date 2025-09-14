package com.loteria360.repository;

import com.loteria360.domain.model.Bolao;
import com.loteria360.domain.model.StatusBolao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BolaoRepository extends JpaRepository<Bolao, String> {

    @Query("SELECT b FROM Bolao b WHERE b.jogo.id = :jogoId AND b.concurso = :concurso")
    Optional<Bolao> findByJogoIdAndConcurso(@Param("jogoId") String jogoId, @Param("concurso") String concurso);

    @Query("SELECT b FROM Bolao b WHERE b.status = :status")
    Page<Bolao> findByStatus(@Param("status") StatusBolao status, Pageable pageable);

    @Query("SELECT b FROM Bolao b WHERE b.jogo.id = :jogoId AND b.status = :status")
    Page<Bolao> findByJogoIdAndStatus(@Param("jogoId") String jogoId, @Param("status") StatusBolao status, Pageable pageable);

    @Query("SELECT b FROM Bolao b WHERE b.dataSorteio BETWEEN :dataInicio AND :dataFim")
    Page<Bolao> findByDataSorteioBetween(@Param("dataInicio") LocalDate dataInicio, 
                                         @Param("dataFim") LocalDate dataFim, 
                                         Pageable pageable);

    @Query("SELECT b FROM Bolao b WHERE b.cotasVendidas < b.cotasTotais AND b.status = 'ABERTO'")
    List<Bolao> findBoloesComCotasDisponiveis();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Bolao b WHERE b.id = :id")
    Optional<Bolao> findByIdWithLock(@Param("id") String id);

    @Query("SELECT b FROM Bolao b WHERE b.jogo.ativo = true AND b.status = :status")
    Page<Bolao> findByJogoAtivoAndStatus(@Param("status") StatusBolao status, Pageable pageable);
}
