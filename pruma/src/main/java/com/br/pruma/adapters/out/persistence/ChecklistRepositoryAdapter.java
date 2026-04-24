package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Checklist;
import com.br.pruma.core.repository.ChecklistRepository;
import com.br.pruma.core.repository.port.ChecklistRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link ChecklistRepositoryPort}
 * delegando ao {@link ChecklistRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class ChecklistRepositoryAdapter implements ChecklistRepositoryPort {

    private final ChecklistRepository checklistRepository;

    @Override
    public Checklist save(Checklist checklist) {
        return checklistRepository.save(checklist);
    }

    @Override
    public Optional<Checklist> findById(Integer id) {
        return checklistRepository.findById(id);
    }

    @Override
    public Optional<Checklist> findByIdWithItens(Integer id) {
        return checklistRepository.findByIdWithItens(id);
    }

    @Override
    public List<Checklist> findAll() {
        return checklistRepository.findAll();
    }

    @Override
    public Page<Checklist> findAll(Pageable pageable) {
        return checklistRepository.findAll(pageable);
    }

    @Override
    public List<Checklist> findByProjetoIdWithItens(Integer projetoId) {
        return checklistRepository.findByProjetoIdWithItens(projetoId);
    }

    @Override
    public void softDelete(Integer id) {
        checklistRepository.softDelete(id);
    }

    @Override
    public boolean existsByNomeAndProjetoIdAndAtivoTrue(String nome, Integer projetoId) {
        return checklistRepository.existsByNomeAndProjetoIdAndAtivoTrue(nome, projetoId);
    }

    @Override
    public boolean existsByNomeAndProjetoIdAndIdNot(String nome, Integer projetoId, Integer id) {
        return checklistRepository.existsByNomeAndProjetoIdAndIdNot(nome, projetoId, id);
    }

    @Override
    public void deleteById(Integer id) {
        checklistRepository.deleteById(id);
    }

    @Override
    public void delete(Checklist checklist) {
        checklistRepository.delete(checklist);
    }

    @Override
    public boolean existsById(Integer id) {
        return checklistRepository.existsById(id);
    }
}
