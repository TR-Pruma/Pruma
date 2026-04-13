package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Notificacao;
import com.br.pruma.core.repository.NotificacaoRepository;
import com.br.pruma.core.repository.port.NotificacaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link NotificacaoRepositoryPort}
 * delegando ao {@link NotificacaoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class NotificacaoRepositoryAdapter implements NotificacaoRepositoryPort {

    private final NotificacaoRepository notificacaoRepository;

    @Override
    public Notificacao save(Notificacao notificacao) {
        return notificacaoRepository.save(notificacao);
    }

    @Override
    public Optional<Notificacao> findById(Integer id) {
        return notificacaoRepository.findById(id);
    }

    @Override
    public List<Notificacao> findAll() {
        return notificacaoRepository.findAll();
    }

    @Override
    public Page<Notificacao> findAll(Pageable pageable) {
        return notificacaoRepository.findAll(pageable);
    }

    @Override
    public List<Notificacao> findAllByCliente_Cpf(String clienteCpf) {
        return notificacaoRepository.findAllByCliente_Cpf(clienteCpf);
    }

    @Override
    public List<Notificacao> findAllByTipoUsuario_Id(Integer tipoUsuarioId) {
        return notificacaoRepository.findAllByTipoUsuario_Id(tipoUsuarioId);
    }

    @Override
    public List<Notificacao> findAllByLida(Boolean lida) {
        return notificacaoRepository.findAllByLida(lida);
    }

    @Override
    public void deleteById(Integer id) {
        notificacaoRepository.deleteById(id);
    }

    @Override
    public void delete(Notificacao notificacao) {
        notificacaoRepository.delete(notificacao);
    }

    @Override
    public boolean existsById(Integer id) {
        return notificacaoRepository.existsById(id);
    }
}
