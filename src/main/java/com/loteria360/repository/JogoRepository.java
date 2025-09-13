package com.loteria360.repository;

import com.loteria360.domain.model.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, UUID> {
    
    Optional<Jogo> findByCodigo(String codigo);
    
    List<Jogo> findByAtivoTrueOrderByNome();
    
    boolean existsByCodigo(String codigo);
}
