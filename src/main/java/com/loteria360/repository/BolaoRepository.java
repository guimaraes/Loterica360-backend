package com.loteria360.repository;

import com.loteria360.domain.model.Bolao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BolaoRepository extends JpaRepository<Bolao, UUID> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Bolao b WHERE b.id = :id")
    Optional<Bolao> findByIdWithLock(@Param("id") UUID id);
    
    List<Bolao> findByStatusOrderByCriadoEmDesc(Bolao.Status status);
    
    List<Bolao> findByJogoIdAndStatusOrderByCriadoEmDesc(UUID jogoId, Bolao.Status status);
}
