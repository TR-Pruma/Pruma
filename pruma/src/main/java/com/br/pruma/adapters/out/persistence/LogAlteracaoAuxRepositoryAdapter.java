package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.LogAlteracaoAux;
import com.br.pruma.core.enums.TipoAlteracao;
import com.br.pruma.core.repository.LogAlteracaoAuxRepository;
import com.br.pruma.core.repository.port.LogAlteracaoAuxRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída (Output Adapter) que implementa o {@link LogAlteracaoAuxRepositoryPort}
 * delegando as operações ao {@link LogAlteracaoAuxRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class LogAlteracaoAuxRepositoryAdapter implements LogAlteracaoAuxRepositoryPort {

    private final LogAlteracaoAuxRepository logAlteracaoAuxRepository;

    @Override
    public LogAlteracaoAux save(LogAlteracaoAux logAlteracaoAux) {
        return logAlteracaoAuxRepository.save(logAlteracaoAux);
    }

    @Override
    public Optional<LogAlteracaoAux> findById(Integer id) {
        return logAlteracaoAuxRepository.findById(id);
    }

    @Override
    public List<LogAlteracaoAux> findAll() {
        return logAlteracaoAuxRepository.findAll();
    }

    @Override
    public List<LogAlteracaoAux> findByTipoAlteracao(TipoAlteracao tipoAlteracao) {
        return logAlteracaoAuxRepository.findByTipoAlteracao(tipoAlteracao);
    }

    @Override
    public void delete(LogAlteracaoAux logAlteracaoAux) {
        logAlteracaoAuxRepository.delete(logAlteracaoAux);
    }

    @Override
    public boolean existsById(Integer id) {
        return logAlteracaoAuxRepository.existsById(id);
    }
}
