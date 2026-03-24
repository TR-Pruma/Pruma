package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.AnexoRequestDTO;
import com.br.pruma.application.dto.response.AnexoResponseDTO;
import com.br.pruma.application.mapper.AnexoMapper;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Anexo;
import com.br.pruma.core.repository.AnexoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnexoService {

    private final AnexoRepository repository;
    private final AnexoMapper mapper;

    public AnexoService(AnexoRepository repository, AnexoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<AnexoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AnexoResponseDTO buscarPorId(Integer id) {
        Anexo anexo = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Anexo com ID " + id + " não encontrado."));
        return mapper.toResponseDTO(anexo);
    }

    public AnexoResponseDTO salvar(AnexoRequestDTO dto) {
        Anexo anexo = mapper.toEntity(dto);
        Anexo salvo = repository.save(anexo);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Não é possível deletar. Anexo com ID " + id + " não existe.");
        }
        repository.deleteById(id);
    }
}
