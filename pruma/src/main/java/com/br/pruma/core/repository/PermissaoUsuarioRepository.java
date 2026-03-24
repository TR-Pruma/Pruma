package com.br.pruma.core.repository;

import com.br.pruma.core.domain.PermissaoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissaoUsuarioRepository extends JpaRepository<PermissaoUsuario, Long> {

    // Cliente.cpf (não clienteCpf)
    List<PermissaoUsuario> findByCliente_Cpf(String cpf);

    // TipoUsuario.id (a PK do TipoUsuario se chama 'id' em Java, coluna tipo_usuario)
    List<PermissaoUsuario> findByTipoUsuario_Id(Integer tipoUsuarioId);

    // Busca permissão específica por cpf do cliente + descrição da permissão
    Optional<PermissaoUsuario> findByCliente_CpfAndPermissao(String cpf, String permissao);
}
