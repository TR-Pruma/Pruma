package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Lembrete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Lembrete.
 */
public interface LembreteRepositoryPort {

    Lembrete save(Lembrete lembrete);

    Optional<Lembrete> findById(Integer id);

    List<Lembrete> findAll();

    Page<Lembrete> findAll(Pageable pageable);

    /** Retorna os lembretes de um cliente ordenados por data/hora. */
    List<Lembrete> findByCliente_CpfOrderByDataHora(String clienteCpf);

    /** Retorna os lembretes de um tipo de usuário ordenados por data/hora. */
    List<Lembrete> findByTipoUsuario_IdOrderByDataHora(Integer tipoUsuarioId);

    void deleteById(Integer id);

    void delete(Lembrete lembrete);

    boolean existsById(Integer id);
}
