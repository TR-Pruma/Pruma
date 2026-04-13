package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Endereco;
import com.br.pruma.core.repository.EnderecoRepository;
import com.br.pruma.core.repository.port.EnderecoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link EnderecoRepositoryPort}
 * delegando ao {@link EnderecoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class EnderecoRepositoryAdapter implements EnderecoRepositoryPort {

    private final EnderecoRepository enderecoRepository;

    @Override
    public Endereco save(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    @Override
    public Optional<Endereco> findById(Integer id) {
        return enderecoRepository.findById(id);
    }

    @Override
    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    @Override
    public Page<Endereco> findAll(Pageable pageable) {
        return enderecoRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer id) {
        enderecoRepository.deleteById(id);
    }

    @Override
    public void delete(Endereco endereco) {
        enderecoRepository.delete(endereco);
    }

    @Override
    public boolean existsById(Integer id) {
        return enderecoRepository.existsById(id);
    }
}
