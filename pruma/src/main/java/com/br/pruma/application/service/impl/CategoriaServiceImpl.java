package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.CategoriaRequestDTO;
import com.br.pruma.application.dto.response.CategoriaResponseDTO;
import com.br.pruma.application.service.CategoriaService;
import com.br.pruma.core.domain.Categoria;
import com.br.pruma.core.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public CategoriaResponseDTO criar(CategoriaRequestDTO request) {
        Categoria entity = Categoria.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .build();
        return toResponse(categoriaRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodos() {
        return categoriaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Integer id) {
        return categoriaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada: " + id));
    }

    @Override
    @Transactional
    public CategoriaResponseDTO atualizar(Integer id, CategoriaRequestDTO request) {
        Categoria entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada: " + id));
        entity.setNome(request.nome());
        entity.setDescricao(request.descricao());
        return toResponse(categoriaRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoria não encontrada: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaResponseDTO toResponse(Categoria e) {
        return new CategoriaResponseDTO(
                e.getId(),
                e.getNome(),
                e.getDescricao(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
