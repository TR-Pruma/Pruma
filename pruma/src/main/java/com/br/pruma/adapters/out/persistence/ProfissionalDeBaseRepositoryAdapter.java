package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.ProfissionalDeBase;
import com.br.pruma.core.repository.ProfissionalDeBaseRepository;
import com.br.pruma.core.repository.port.ProfissionalDeBaseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link ProfissionalDeBaseRepositoryPort}
 * delegando ao {@link ProfissionalDeBaseRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class ProfissionalDeBaseRepositoryAdapter implements ProfissionalDeBaseRepositoryPort {

    private final ProfissionalDeBaseRepository profissionalDeBaseRepository;

    @Override
    public ProfissionalDeBase save(ProfissionalDeBase profissionalDeBase) {
        return profissionalDeBaseRepository.save(profissionalDeBase);
    }

    @Override
    public Optional<ProfissionalDeBase> findById(Integer id) {
        return profissionalDeBaseRepository.findById(id);
    }

    @Override
    public List<ProfissionalDeBase> findAll() {
        return profissionalDeBaseRepository.findAll();
    }

    @Override
    public Page<ProfissionalDeBase> findAll(Pageable pageable) {
        return profissionalDeBaseRepository.findAll(pageable);
    }

    @Override
    public List<ProfissionalDeBase> findAllByNomeContainingIgnoreCase(String nome) {
        return profissionalDeBaseRepository.findAllByNomeContainingIgnoreCase(nome);
    }

    @Override
    public List<ProfissionalDeBase> findAllByEspecialidade(String especialidade) {
        return profissionalDeBaseRepository.findAllByEspecialidade(especialidade);
    }

    @Override
    public List<ProfissionalDeBase> findAllByEspecialidadeContainingIgnoreCase(String especialidade) {
        return profissionalDeBaseRepository.findAllByEspecialidadeContainingIgnoreCase(especialidade);
    }

    @Override
    public Optional<ProfissionalDeBase> findByTelefone(String telefone) {
        return profissionalDeBaseRepository.findByTelefone(telefone);
    }

    @Override
    public Optional<ProfissionalDeBase> findByCpf(String cpf) {
        return profissionalDeBaseRepository.findByCpf(cpf);
    }

    @Override
    public boolean existsByNome(String nome) {
        return profissionalDeBaseRepository.existsByNome(nome);
    }

    @Override
    public void deleteById(Integer id) {
        profissionalDeBaseRepository.deleteById(id);
    }

    @Override
    public void delete(ProfissionalDeBase profissionalDeBase) {
        profissionalDeBaseRepository.delete(profissionalDeBase);
    }

    @Override
    public boolean existsById(Integer id) {
        return profissionalDeBaseRepository.existsById(id);
    }
}
