package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.TipoDocumento;
import com.br.pruma.core.repository.TipoDocumentoRepository;
import com.br.pruma.core.repository.port.TipoDocumentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link TipoDocumentoRepositoryPort}
 * delegando ao {@link TipoDocumentoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class TipoDocumentoRepositoryAdapter implements TipoDocumentoRepositoryPort {

    private final TipoDocumentoRepository tipoDocumentoRepository;

    @Override
    public TipoDocumento save(TipoDocumento tipoDocumento) {
        return tipoDocumentoRepository.save(tipoDocumento);
    }

    @Override
    public Optional<TipoDocumento> findById(Integer id) {
        return tipoDocumentoRepository.findById(id);
    }

    @Override
    public List<TipoDocumento> findAll() {
        return tipoDocumentoRepository.findAll();
    }

    @Override
    public Page<TipoDocumento> findAll(Pageable pageable) {
        return tipoDocumentoRepository.findAll(pageable);
    }

    @Override
    public List<TipoDocumento> findByDescricaoContainingIgnoreCase(String descricao) {
        return tipoDocumentoRepository.findByDescricaoContainingIgnoreCase(descricao);
    }

    @Override
    public void deleteById(Integer id) {
        tipoDocumentoRepository.deleteById(id);
    }

    @Override
    public void delete(TipoDocumento tipoDocumento) {
        tipoDocumentoRepository.delete(tipoDocumento);
    }

    @Override
    public boolean existsById(Integer id) {
        return tipoDocumentoRepository.existsById(id);
    }
}
