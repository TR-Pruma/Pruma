package com.br.pruma.application.service;

import com.br.pruma.core.domain.Checklist;
import com.br.pruma.core.repository.ChecklistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChecklistService {
    private final ChecklistRepository repository;

    public ChecklistService(ChecklistRepository repository) {
        this.repository = repository;
    }

    public List<Checklist> listarTodos() {
        return repository.findAll();
    }

    public Optional<Checklist> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public Checklist salvar(Checklist checklist) {
        return repository.save(checklist);
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }
}