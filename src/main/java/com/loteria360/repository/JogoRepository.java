package com.loteria360.repository;

import com.loteria360.domain.model.Jogo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, String> {

    Optional<Jogo> findByNome(String nome);

    boolean existsByNome(String nome);

    @Query("SELECT j FROM Jogo j WHERE j.ativo = :ativo")
    Page<Jogo> findByAtivo(@Param("ativo") Boolean ativo, Pageable pageable);

    @Query("SELECT j FROM Jogo j WHERE j.nome LIKE %:nome% AND j.ativo = true")
    Page<Jogo> findByNomeContainingAndAtivo(@Param("nome") String nome, Pageable pageable);

    List<Jogo> findByAtivoTrue();

    long countByAtivoTrue();
}
