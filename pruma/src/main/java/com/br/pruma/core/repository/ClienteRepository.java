package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Page<Cliente> findByAtivoTrue(Pageable pageable);
    Optional<Cliente> findByIdAndAtivoTrue(Integer id);
    Optional<Cliente> findByCpfAndAtivoTrue(String cpf);
    Optional<Cliente> findByEmailAndAtivoTrue(String email);
    boolean existsByCpfAndAtivoTrue(String cpf);
    boolean existsByEmailAndAtivoTrue(String email);
}

