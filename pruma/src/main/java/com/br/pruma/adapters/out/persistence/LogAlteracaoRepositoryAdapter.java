package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.LogAlteracao;
import com.br.pruma.core.repository.LogAlteracaoRepository;
import com.br.pruma.core.repository.port.LogAlteracaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link LogAlteracaoRepositoryPort}
 * delegando ao {@link LogAlteracaoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class LogAlteracaoRepositoryAdapter implements LogAlteracaoRepositoryPort {

    private final LogAlteracaoRepository logAlteracaoRepository;

    @Override
    public LogAlteracao save(LogAlteracao logAlteracao) {
        return logAlteracaoRepository.save(logAlteracao);
    }

    @Override
    public Optional<LogAlteracao> findById(Integer id) {
        return logAlteracaoRepository.findById(id);
    }

    @Override
    public List<LogAlteracao> findAll() {
        return logAlteracaoRepository.findAll();
    }

    @Override
    public Page<LogAlteracao> findAll(Pageable pageable) {
        return logAlteracaoRepository.findAll(pageable);
    }

    @Override
    public Page<LogAlteracao> findByProjetoIdOrderByDataHoraDesc(Integer projetoId, Pageable pageable) {
        return logAlteracaoRepository.findByProjeto_IdOrderByDataHoraDesc(projetoId, pageable);
    }

    @Override
    public Page<LogAlteracao> findByClienteCpfOrderByDataHoraDesc(String clienteCpf, Pageable pageable) {
        return logAlteracaoRepository.findByCliente_CpfOrderByDataHoraDesc(clienteCpf, pageable);
    }

    @Override
    public Page<LogAlteracao> findByTipoUsuarioIdOrderByDataHoraDesc(Integer tipoUsuarioId, Pageable pageable) {
        return logAlteracaoRepository.findByTipoUsuario_IdOrderByDataHoraDesc(tipoUsuarioId, pageable);
    }

    @Override
    public void deleteById(Integer id) {
        logAlteracaoRepository.deleteById(id);
    }

    @Override
    public void delete(LogAlteracao logAlteracao) {
        logAlteracaoRepository.delete(logAlteracao);
    }

    @Override
    public boolean existsById(Integer id) {
        return logAlteracaoRepository.existsById(id);
    }
}
