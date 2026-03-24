package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.InsumoRequestDTO;
import com.br.pruma.application.dto.response.InsumoResponseDTO;
import com.br.pruma.application.mapper.InsumoMapper;
import com.br.pruma.application.service.InsumoService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Insumo;
import com.br.pruma.core.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsumoServiceImpl implements InsumoService {

    private final InsumoRepository repository;
    private final InsumoMapper mapper;

    @Override
    public InsumoResponseDTO create(InsumoRequestDTO dto) {
        Insumo insumo = mapper.toEntity(dto);
        return mapper.toResponseDTO(repository.save(insumo));
    }

    @Override
    public InsumoResponseDTO getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Insumo com ID " + id + " não encontrado."));
    }

    @Override
    public List<InsumoResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public InsumoResponseDTO update(Integer id, InsumoRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Insumo com ID " + id + " não encontrado."));
        Insumo insumo = mapper.toEntity(dto);
        insumo.setId(id);
        return mapper.toResponseDTO(repository.save(insumo));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Não é possível deletar. Insumo com ID " + id + " não existe.");
        }
        repository.deleteById(id);
    }
}
