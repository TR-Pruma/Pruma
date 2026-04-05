package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.FeedbackRequestDTO;
import com.br.pruma.application.dto.response.FeedbackResponseDTO;
import com.br.pruma.application.dto.update.FeedbackUpdateDTO;
import com.br.pruma.application.mapper.FeedbackMapper;
import com.br.pruma.application.service.FeedbackService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Feedback;
import com.br.pruma.core.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository repository;
    private final FeedbackMapper mapper;

    @Override
    public FeedbackResponseDTO create(FeedbackRequestDTO dto) {
        Feedback entity = mapper.toEntity(dto);
        return mapper.toResponseDTODTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public FeedbackResponseDTO getById(Integer id) {
        return mapper.toResponseDTO(repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Feedback com ID " + id + " não encontrado.")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedbackResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Override
    public FeedbackResponseDTO update(Integer id, FeedbackUpdateDTO dto) {
        Feedback entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Feedback com ID " + id + " não encontrado."));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponseDTO(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        Feedback entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Feedback com ID " + id + " não encontrado."));
        repository.delete(entity);
    }
}
