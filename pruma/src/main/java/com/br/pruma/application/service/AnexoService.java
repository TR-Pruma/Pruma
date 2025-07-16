package com.br.pruma.application.service;


import com.br.pruma.core.domain.Anexo;
import com.br.pruma.core.repository.AnexoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AnexoService {
    private final AnexoRepository anexoRepository;

    public AnexoService(AnexoRepository anexoRepository) {
        this.anexoRepository = anexoRepository;
    }

    public List<Anexo> listarTodos() {
        return anexoRepository.findAll();
    }

    public Optional<Anexo> buscarPorId(Long id) {
        return anexoRepository.findById(id);
    }

    public Anexo salvar(Anexo anexo) {
        return anexoRepository.save(anexo);
    }

    public void deletar(Long id) {
        anexoRepository.deleteById(id);
    }
}
