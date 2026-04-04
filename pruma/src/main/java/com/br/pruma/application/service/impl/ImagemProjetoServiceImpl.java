package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ImagemProjetoRequestDTO;
import com.br.pruma.application.dto.response.ImagemProjetoResponseDTO;
import com.br.pruma.application.dto.update.ImagemProjetoUpdateDTO;
import com.br.pruma.application.mapper.ImagemProjetoMapper;
import com.br.pruma.application.service.ImagemProjetoService;
import com.br.pruma.core.domain.ImagemProjeto;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ImagemProjetoRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ImagemProjetoServiceImpl implements ImagemProjetoService {

    private final ImagemProjetoRepository repository;
    private final ProjetoRepository projetoRepository;
    private final ImagemProjetoMapper mapper;

    @Override
    public ImagemProjetoResponseDTO create(ImagemProjetoRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
        ImagemProjeto entity = mapper.toEntity(dto);
        entity.setProjeto(projeto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ImagemProjetoResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ImagemProjeto não encontrada: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImagemProjetoResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImagemProjetoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImagemProjetoResponseDTO> listByProjeto(Integer projetoId) {
        return repository.findAllByProjeto_Id(projetoId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public ImagemProjetoResponseDTO update(Integer id, ImagemProjetoUpdateDTO dto) {
        ImagemProjeto entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ImagemProjeto não encontrada: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("ImagemProjeto não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
