package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {

    // Cliente.cpf é String (11 dígitos) — mesmo padrão dos outros repositories
    List<Notificacao> findAllByCliente_Cpf(String clienteCpf);

    List<Notificacao> findAllByTipoUsuario_Id(Integer tipoUsuarioId);

    List<Notificacao> findAllByLida(Boolean lida);
}
