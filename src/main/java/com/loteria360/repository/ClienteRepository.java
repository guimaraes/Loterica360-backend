package com.loteria360.repository;

import com.loteria360.domain.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByEmail(String email);

    @Query("SELECT c FROM Cliente c WHERE c.nome LIKE %:nome%")
    Page<Cliente> findByNomeContaining(@Param("nome") String nome, Pageable pageable);

    @Query("SELECT c FROM Cliente c WHERE c.consentimentoLgpd = :consentimento")
    Page<Cliente> findByConsentimentoLgpd(@Param("consentimento") Boolean consentimento, Pageable pageable);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
