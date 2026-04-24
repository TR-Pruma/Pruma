package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.TipoUsuario;
import com.br.pruma.core.repository.TipoUsuarioRepository;
import com.br.pruma.core.repository.port.TipoUsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link TipoUsuarioRepositoryPort}
 * delegando ao {@link TipoUsuarioRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class TipoUsuarioRepositoryAdapter implements TipoUsuarioRepositoryPort {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    @Override
    public TipoUsuario save(TipoUsuario tipoUsuario) {
        return tipoUsuarioRepository.save(tipoUsuario);
    }

    @Override
    public Optional<TipoUsuario> findById(Integer id) {
        return tipoUsuarioRepository.findById(id);
    }

    @Override
    public List<TipoUsuario> findAll() {
        return tipoUsuarioRepository.findAll();
    }

    @Override
    public Page<TipoUsuario> findAll(Pageable pageable) {
        return tipoUsuarioRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer id) {
        tipoUsuarioRepository.deleteById(id);
    }

    @Override
    public void delete(TipoUsuario tipoUsuario) {
        tipoUsuarioRepository.delete(tipoUsuario);
    }

    @Override
    public boolean existsById(Integer id) {
        return tipoUsuarioRepository.existsById(id);
    }
}
