package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {

    /**
     * Lista todas as notificações de um cliente específico.
     */
    List<Notificacao> findAllByCliente_Cpf(Long clienteCpf);

    /**
     * Lista todas as notificações de um tipo de usuário específico.
     */
    List<Notificacao> findAllByTipoUsuario_Id(Integer tipoUsuarioId);

    /**
     * Lista todas as notificações filtradas por se foram lidas ou não.
     */
    List<Notificacao> findAllByLida(Boolean lida);
}

