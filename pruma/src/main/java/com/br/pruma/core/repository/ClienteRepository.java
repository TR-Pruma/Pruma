package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // cpf é String na entidade Cliente
    Optional<Cliente> findByCpf(String cpf);
}
