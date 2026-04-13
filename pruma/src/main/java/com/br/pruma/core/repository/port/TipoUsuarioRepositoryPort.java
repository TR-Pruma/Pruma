package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.TipoUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de TipoUsuario.
 */
public interface TipoUsuarioRepositoryPort {

    TipoUsuario save(TipoUsuario tipoUsuario);

    Optional<TipoUsuario> findById(Integer id);

    List<TipoUsuario> findAll();

    Page<TipoUsuario> findAll(Pageable pageable);

    void deleteById(Integer id);

    void delete(TipoUsuario tipoUsuario);

    boolean existsById(Integer id);
}
