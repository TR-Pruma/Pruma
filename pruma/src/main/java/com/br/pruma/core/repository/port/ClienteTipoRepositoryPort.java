package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.ClienteTipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de ClienteTipo.
 */
public interface ClienteTipoRepositoryPort {

    ClienteTipo save(ClienteTipo clienteTipo);

    Optional<ClienteTipo> findById(Integer id);

    /** Busca apenas os tipos ativos. */
    List<ClienteTipo> findByAtivoTrue();

    /** Busca tipo ativo pelo id. */
    Optional<ClienteTipo> findByIdAndAtivoTrue(Integer id);

    List<ClienteTipo> findAll();

    Page<ClienteTipo> findAll(Pageable pageable);

    /** Verifica se já existe tipo ativo com a descrição informada. */
    boolean existsByDescricaoClienteAndAtivoTrue(String descricaoCliente);

    void deleteById(Integer id);

    void delete(ClienteTipo clienteTipo);

    boolean existsById(Integer id);
}
