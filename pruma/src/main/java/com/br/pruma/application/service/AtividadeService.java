package com.br.pruma.application.service;

import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.repository.AtividadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtividadeService {

    private final AtividadeRepository repository;

    public AtividadeService(AtividadeRepository repository) {
        this.repository = repository;
    }

    public List<Atividade> listarTodos() {
        return repository.findAll();
    }

    public Optional<Atividade> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public Atividade salvar(Atividade atividade) {
        return repository.save(atividade);
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }
}
