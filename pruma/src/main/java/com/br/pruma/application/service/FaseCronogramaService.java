package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.FaseCronogramaRequestDTO;
import com.br.pruma.application.dto.response.FaseCronogramaResponseDTO;
import com.br.pruma.application.mapper.FaseCronogramaMapper;
import com.br.pruma.core.domain.FaseCronograma;
import com.br.pruma.infra.repository.FaseCronogramaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FaseCronogramaService {

    private final FaseCronogramaRepository repository;
    private final FaseCronogramaMapper mapper;

    @Transactional
    public FaseCronogramaResponseDTO criar(FaseCronogramaRequestDTO dto) {
        FaseCronograma entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public FaseCronogramaResponseDTO buscarPorId(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("FaseCronograma não encontrada"));
    }
    @Transactional(readOnly = true)
    public List<FaseCronogramaResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }
    @Transactional
    public FaseCronogramaResponseDTO atualizar(Integer id, FaseCronogramaRequestDTO dto) {
        FaseCronograma entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FaseCronograma não encontrada"));

        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setDataInicio(dto.getDataInicio());
        entity.setDataFim(dto.getDataFim());

        repository.save(entity);
        return mapper.toResponseDTO(entity);
    }
    @Transactional
    public void deletar(Integer id) {
        FaseCronograma entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FaseCronograma não encontrada"));
        repository.delete(entity);
    }

}
