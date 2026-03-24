package com.br.pruma.application.service;

import com.br.pruma.core.domain.Auditoria;
import com.br.pruma.core.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuditoriaService {

    private final AuditoriaRepository repository;

    public AuditoriaService(AuditoriaRepository repository) {
        this.repository = repository;
    }

    public List<Auditoria> listarTodos() {
        return repository.findAll();
    }

    public Optional<Auditoria> buscarPorId(UUID id) {
        return repository.findById(id);
    }

    public Auditoria salvar(Auditoria auditoria) {
        return repository.save(auditoria);
    }

    public Optional<Auditoria> atualizar(UUID id, Auditoria dadosNovos) {
        return repository.findById(id).map(existente -> {
            dadosNovos.setId(id);
            return repository.save(dadosNovos);
        });
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }
}
