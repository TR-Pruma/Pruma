package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.MensagemInstantaneaAux;
import com.br.pruma.core.repository.MensagemInstantaneaAuxRepository;
import com.br.pruma.core.repository.port.MensagemInstantaneaAuxRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link MensagemInstantaneaAuxRepositoryPort}
 * delegando ao {@link MensagemInstantaneaAuxRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class MensagemInstantaneaAuxRepositoryAdapter implements MensagemInstantaneaAuxRepositoryPort {

    private final MensagemInstantaneaAuxRepository mensagemInstantaneaAuxRepository;

    @Override
    public MensagemInstantaneaAux save(MensagemInstantaneaAux mensagemInstantaneaAux) {
        return mensagemInstantaneaAuxRepository.save(mensagemInstantaneaAux);
    }

    @Override
    public Optional<MensagemInstantaneaAux> findById(Integer id) {
        return mensagemInstantaneaAuxRepository.findById(id);
    }

    @Override
    public List<MensagemInstantaneaAux> findAll() {
        return mensagemInstantaneaAuxRepository.findAll();
    }

    @Override
    public Page<MensagemInstantaneaAux> findAll(Pageable pageable) {
        return mensagemInstantaneaAuxRepository.findAll(pageable);
    }

    @Override
    public Optional<MensagemInstantaneaAux> findByMensagemId(Integer mensagemId) {
        return mensagemInstantaneaAuxRepository.findByMensagem_Id(mensagemId);
    }

    @Override
    public void deleteById(Integer id) {
        mensagemInstantaneaAuxRepository.deleteById(id);
    }

    @Override
    public void delete(MensagemInstantaneaAux mensagemInstantaneaAux) {
        mensagemInstantaneaAuxRepository.delete(mensagemInstantaneaAux);
    }

    @Override
    public boolean existsById(Integer id) {
        return mensagemInstantaneaAuxRepository.existsById(id);
    }
}
