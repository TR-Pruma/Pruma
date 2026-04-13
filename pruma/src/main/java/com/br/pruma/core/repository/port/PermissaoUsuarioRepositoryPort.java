package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.PermissaoUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de PermissaoUsuario.
 * A PK é Long.
 */
public interface PermissaoUsuarioRepositoryPort {

    PermissaoUsuario save(PermissaoUsuario permissaoUsuario);

    Optional<PermissaoUsuario> findById(Long id);

    List<PermissaoUsuario> findAll();

    Page<PermissaoUsuario> findAll(Pageable pageable);

    /** Lista permissões pelo CPF do cliente. */
    List<PermissaoUsuario> findByCliente_Cpf(String cpf);

    /** Lista permissões pelo id do tipo de usuário. */
    List<PermissaoUsuario> findByTipoUsuario_Id(Integer tipoUsuarioId);

    /** Busca permissão específica por CPF e descrição da permissão. */
    Optional<PermissaoUsuario> findByCliente_CpfAndPermissao(String cpf, String permissao);

    void deleteById(Long id);

    void delete(PermissaoUsuario permissaoUsuario);

    boolean existsById(Long id);
}
