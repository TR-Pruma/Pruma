package com.br.pruma.core.repository;

import com.br.pruma.core.domain.PermissaoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissaoUsuarioRepository extends JpaRepository<PermissaoUsuario, Long> {

    // Busca permissões de um cliente específico (por CPF)
    List<PermissaoUsuario> findByCliente_ClienteCpf(String clienteCpf);

    // Busca permissões filtrando pelo tipo de usuário
    List<PermissaoUsuario> findByTipoUsuario_TipoUsuario(Integer tipoUsuarioId);

    // Busca uma permissão específica pelo cliente e pela descrição da permissão
    Optional<PermissaoUsuario> findByCliente_ClienteCpfAndPermissao(String clienteCpf, String permissao);
}
