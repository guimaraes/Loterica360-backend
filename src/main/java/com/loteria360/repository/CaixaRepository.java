package com.loteria360.repository;

import com.loteria360.domain.model.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaixaRepository extends JpaRepository<Caixa, String> {
    
    Optional<Caixa> findByNumero(Integer numero);
    
    boolean existsByNumero(Integer numero);

    long countByAtivoTrue();
}
