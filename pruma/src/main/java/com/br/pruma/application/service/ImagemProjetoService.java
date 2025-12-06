package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ImagemProjetoRequestDTO;
import com.br.pruma.application.dto.response.ImagemProjetoResponseDTO;
import com.br.pruma.application.mapper.ImagemProjetoMapper;
import com.br.pruma.core.domain.ImagemProjeto;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ImagemProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagemProjetoService {

    private final ImagemProjetoRepository repository;
    private final ImagemProjetoMapper mapper;

    @Transactional(readOnly = true)
    public List<ImagemProjetoResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }
    @Transactional(readOnly = true)
    public ImagemProjetoResponseDTO buscarPorId(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("ImagemProjeto não encontrada"));
    }
    @Transactional
    public ImagemProjetoResponseDTO criar(ImagemProjetoRequestDTO dto) {
        ImagemProjeto entity = mapper.toEntity(dto);
        return mapper.toResponseDTO(repository.save(entity));
    }
    @Transactional
    public ImagemProjetoResponseDTO atualizar(Integer id, ImagemProjetoRequestDTO dto) {
        ImagemProjeto existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ImagemProjeto não encontrada"));


        existente.setCaminhoArquivo(dto.caminhoArquivo());
        existente.setDescricao(dto.descricao());
        existente.setDataUpload(dto.dataUpload());

        return mapper.toResponseDTO(repository.save(existente));
    }
    @Transactional
    public void deletar(Integer id) {
        repository.deleteById(id);
    }


}
