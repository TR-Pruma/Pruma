package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.FaseCronogramaRequestDTO;
import com.br.pruma.application.dto.response.FaseCronogramaResponseDTO;
import com.br.pruma.application.dto.update.FaseCronogramaUpdateDTO;
import com.br.pruma.application.mapper.FaseCronogramaMapper;
import com.br.pruma.application.service.FaseCronogramaService;
import com.br.pruma.core.domain.Cronograma;
import com.br.pruma.core.domain.FaseCronograma;
import com.br.pruma.core.repository.CronogramaRepository;
import com.br.pruma.core.repository.FaseCronogramaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FaseCronogramaServiceImpl implements FaseCronogramaService {

    private final FaseCronogramaRepository repository;
    private final CronogramaRepository cronogramaRepository;
    private final FaseCronogramaMapper mapper;

    @Override
    public FaseCronogramaResponseDTO create(FaseCronogramaRequestDTO dto) {
        Cronograma cronograma = cronogramaRepository.findById(dto.getCronogramaId())
                .orElseThrow(() -> new EntityNotFoundException("Cronograma não encontrado: " + dto.getCronogramaId()));
        FaseCronograma entity = mapper.toEntity(dto);
        entity.setCronograma(cronograma);
        return mapper.toResponseDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public FaseCronogramaResponseDTO getById(Integer id) {
        return mapper.toResponseDTO(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FaseCronograma não encontrada: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FaseCronogramaResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FaseCronogramaResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FaseCronogramaResponseDTO> listByCronograma(Integer cronogramaId) {
        return repository.findAllByCronograma_Id(cronogramaId).stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public FaseCronogramaResponseDTO update(Integer id, FaseCronogramaUpdateDTO dto) {
        FaseCronograma entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FaseCronograma não encontrada: " + id));
        if (dto.getCronogramaId() != null) {
            Cronograma cronograma = cronogramaRepository.findById(dto.getCronogramaId())
                    .orElseThrow(() -> new EntityNotFoundException("Cronograma não encontrado: " + dto.getCronogramaId()));
            entity.setCronograma(cronograma);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponseDTO(repository.save(entity));
    }

    @Override
    public FaseCronogramaResponseDTO replace(Integer id, FaseCronogramaRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FaseCronograma não encontrada: " + id));
        Cronograma cronograma = cronogramaRepository.findById(dto.getCronogramaId())
                .orElseThrow(() -> new EntityNotFoundException("Cronograma não encontrado: " + dto.getCronogramaId()));
        FaseCronograma entity = mapper.toEntity(dto);
        entity.setId(id);
        entity.setCronograma(cronograma);
        return mapper.toResponseDTO(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        FaseCronograma entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FaseCronograma não encontrada: " + id));
        entity.setAtivo(false);
        repository.save(entity);
    }
}