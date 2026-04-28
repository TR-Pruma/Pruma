package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Notificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Notificacao.
 */
public interface NotificacaoRepositoryPort {

    Notificacao save(Notificacao notificacao);

    Optional<Notificacao> findById(Integer id);

    List<Notificacao> findAll();

    Page<Notificacao> findAll(Pageable pageable);

    /** Lista notificações de um cliente pelo CPF. */
    List<Notificacao> findAllByClienteCpf(String clienteCpf);

    /** Lista notificações por tipo de usuário. */
    List<Notificacao> findAllByTipoUsuarioId(Integer tipoUsuarioId);

    /** Lista notificações pelo status de leitura. */
    List<Notificacao> findAllByLida(Boolean lida);

    void deleteById(Integer id);

    void delete(Notificacao notificacao);

    boolean existsById(Integer id);
}
