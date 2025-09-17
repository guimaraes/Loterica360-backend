package com.loteria360.repository;

import com.loteria360.domain.model.Bolao;
import com.loteria360.domain.model.StatusBolao;
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
public interface BolaoRepository extends JpaRepository<Bolao, String> {

    @Query("SELECT b FROM Bolao b WHERE b.jogo.id = :jogoId AND b.concurso = :concurso")
    Optional<Bolao> findByJogoIdAndConcurso(@Param("jogoId") String jogoId, @Param("concurso") String concurso);

    @Query("SELECT b FROM Bolao b WHERE b.status = :status")
    List<Bolao> findByStatus(@Param("status") StatusBolao status);

    @Query("SELECT b FROM Bolao b WHERE b.jogo.id = :jogoId AND b.status = :status")
    List<Bolao> findByJogoIdAndStatus(@Param("jogoId") String jogoId, @Param("status") StatusBolao status);

    @Query("SELECT b FROM Bolao b WHERE b.dataSorteio = :dataSorteio")
    List<Bolao> findByDataSorteio(@Param("dataSorteio") LocalDate dataSorteio);

    @Query("SELECT b FROM Bolao b WHERE " +
           "(:search IS NULL OR :search = '' OR " +
           "LOWER(b.concurso) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(b.descricao) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(b.jogo.nome) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Bolao> findBySearchTerm(@Param("search") String search, Pageable pageable);

    @Query("SELECT b FROM Bolao b WHERE b.status = 'ABERTO'")
    List<Bolao> findBoloesAtivos();

    long countByStatus(com.loteria360.domain.model.StatusBolao status);
}
