package com.br.pruma.core.repository;

import com.br.pruma.core.domain.ClienteTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteTipoRepository extends JpaRepository<ClienteTipo, Integer> {
    boolean existsByDescricaoClienteAndAtivoTrue(String descricaoCliente);
    List<ClienteTipo> findByAtivoTrue();
    Optional<ClienteTipo> findByIdAndAtivoTrue(Integer id);
}
