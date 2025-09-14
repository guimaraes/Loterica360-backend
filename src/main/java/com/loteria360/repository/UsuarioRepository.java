package com.loteria360.repository;

import com.loteria360.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.ativo = :ativo")
    Page<Usuario> findByAtivo(@Param("ativo") Boolean ativo, Pageable pageable);

    @Query("SELECT u FROM Usuario u WHERE u.papel = :papel AND u.ativo = true")
    Page<Usuario> findByPapelAndAtivo(@Param("papel") String papel, Pageable pageable);
}
