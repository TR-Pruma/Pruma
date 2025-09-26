package com.br.pruma.application.service;

import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.repository.AtividadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.br.pruma.application.dto.request.AtividadeRequestDTO;
import com.br.pruma.application.dto.response.AtividadeResponseDTO;
import com.br.pruma.application.mapper.AtividadeMapper;
import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.repository.AtividadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository repository;
    private final AtividadeMapper mapper;

    /**
     * Retorna todas as atividades mapeadas para DTO.
     */
    @Transactional(readOnly = true)
    public List<AtividadeResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    /**
     * Busca uma atividade por ID ou lança 404 se não existir.
     */
    @Transactional(readOnly = true)
    public AtividadeResponseDTO findById(Integer id) {
        Atividade atividade = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Atividade não encontrada com id " + id
                        )
                );
        return mapper.toResponseDTO(atividade);
    }

    /**
     * Cria uma nova atividade a partir do DTO e retorna o DTO de resposta.
     */
    @Transactional
    public AtividadeResponseDTO create(AtividadeRequestDTO dto) {
        Atividade entidade = mapper.toEntity(dto);
        Atividade salvo = repository.save(entidade);
        return mapper.toResponseDTO(salvo);
    }

    /**
     * Atualiza a atividade existente com os dados do DTO ou lança 404 se não existir.
     */
    @Transactional
    public AtividadeResponseDTO update(Integer id, AtividadeRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Atividade não encontrada com id " + id
            );
        }

        Atividade entidade = mapper.toEntity(dto);
        entidade.setId(id);
        Atividade salvo = repository.save(entidade);
        return mapper.toResponseDTO(salvo);
    }

    /**
     * Remove a atividade ou lança 404 se não existir.
     */
    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Atividade não encontrada com id " + id
            );
        }
        repository.deleteById(id);
    }
}

