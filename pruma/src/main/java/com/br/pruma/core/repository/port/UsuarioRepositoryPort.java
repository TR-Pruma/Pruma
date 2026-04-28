package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.TipoUsuario;
import com.br.pruma.core.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Usuario.
 */
public interface UsuarioRepositoryPort {

    Usuario save(Usuario usuario);

    Optional<Usuario> findById(Integer id);

    List<Usuario> findAll();

    Page<Usuario> findAll(Pageable pageable);

    /** Busca usuário pelo CPF. */
    Optional<Usuario> findByCpf(String cpf);

    /** Verifica se existe usuário com o CPF informado. */
    boolean existsByCpf(String cpf);

    /** Busca usuário pelo CPF e tipo. */
    Optional<Usuario> findByCpfAndTipo(String cpf, TipoUsuario tipo);

    void deleteById(Integer id);

    void delete(Usuario usuario);

    boolean existsById(Integer id);
}
