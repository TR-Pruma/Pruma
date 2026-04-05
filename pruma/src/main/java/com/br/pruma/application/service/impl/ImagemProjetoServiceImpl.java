package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ImagemProjetoRequestDTO;
import com.br.pruma.application.dto.response.ImagemProjetoResponseDTO;
import com.br.pruma.application.dto.update.ImagemProjetoUpdateDTO;
import com.br.pruma.application.mapper.ImagemProjetoMapper;
import com.br.pruma.application.service.ImagemProjetoService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.ImagemProjeto;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ImagemProjetoRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ImagemProjetoServiceImpl implements ImagemProjetoService {

    private final ImagemProjetoRepository repository;
    private final ProjetoRepository projetoRepository;
    private final ImagemProjetoMapper mapper;

    @Override
    public ImagemProjetoResponseDTO create(ImagemProjetoRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.projetoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Projeto com ID " + dto.projetoId() + " não encontrado."));
        ImagemProjeto entity = mapper.toEntity(dto);
        entity.setProjeto(projeto);
        return mapper.toResponseDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ImagemProjetoResponseDTO getById(Integer id) {
        return mapper.toResponseDTO(repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "ImagemProjeto com ID " + id + " não encontrada.")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImagemProjetoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImagemProjetoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImagemProjetoResponseDTO> listByProjeto(Integer projetoId) {
        return repository.findAllByProjeto_Id(projetoId).stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public ImagemProjetoResponseDTO update(Integer id, ImagemProjetoUpdateDTO dto) {
        ImagemProjeto entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "ImagemProjeto com ID " + id + " não encontrada."));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponseDTO(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        ImagemProjeto entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "ImagemProjeto com ID " + id + " não encontrada."));
        repository.delete(entity);
    }
}
