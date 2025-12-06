package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ProjetoCategoriaRequestDTO;
import com.br.pruma.application.dto.response.ProjetoCategoriaResponseDTO;
import com.br.pruma.application.mapper.ProjetoCategoriaMapper;
import com.br.pruma.core.domain.ProjetoCategoria;
import com.br.pruma.core.repository.ProjetoCategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetoCategoriaService {

    private final ProjetoCategoriaRepository repository;
    private final ProjetoCategoriaMapper mapper;

    /**
     * Retorna todas as categorias como DTOs.
     */
    @Transactional(readOnly = true)
    public List<ProjetoCategoriaResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Busca uma categoria por id ou lança 404.
     */
    @Transactional(readOnly = true)
    public ProjetoCategoriaResponseDTO findById(Integer id) {
        ProjetoCategoria entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "ProjetoCategoria não encontrada com id " + id
                ));
        return mapper.toDTO(entity);
    }

    /**
     * Cria uma nova categoria.
     */
    @Transactional
    public ProjetoCategoriaResponseDTO create(ProjetoCategoriaRequestDTO dto) {
        ProjetoCategoria entity = mapper.toEntity(dto);
        ProjetoCategoria saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    /**
     * Atualiza uma categoria existente (patch sem sobrescrever campos nulos).
     */
    @Transactional
    public ProjetoCategoriaResponseDTO update(Integer id, ProjetoCategoriaRequestDTO dto) {
        ProjetoCategoria existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "ProjetoCategoria não encontrada com id " + id
                ));
        mapper.updateFromDto(dto, existing);
        ProjetoCategoria saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    /**
     * Remove uma categoria por id.
     */
    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "ProjetoCategoria não encontrada com id " + id
            );
        }
        repository.deleteById(id);
    }
}

