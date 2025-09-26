package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.TipoDocumentoRequestDTO;
import com.br.pruma.application.dto.response.TipoDocumentoResponseDTO;
import com.br.pruma.application.mapper.TipoDocumentoMapper;
import com.br.pruma.core.domain.TipoDocumento;
import com.br.pruma.infra.repository.TipoDocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoDocumentoService {

    private final TipoDocumentoRepository repository;
    private final TipoDocumentoMapper mapper;

    /**
     * Retorna todos os tipos de documento como DTOs.
     */
    @Transactional(readOnly = true)
    public List<TipoDocumentoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Busca um tipo de documento por ID ou lança 404 se não existir.
     */
    @Transactional(readOnly = true)
    public TipoDocumentoResponseDTO findById(Integer id) {
        TipoDocumento entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "TipoDocumento não encontrado com id " + id
                        )
                );
        return mapper.toDTO(entity);
    }

    /**
     * Cria um novo tipo de documento e retorna o DTO de resposta.
     */
    @Transactional
    public TipoDocumentoResponseDTO create(TipoDocumentoRequestDTO dto) {
        TipoDocumento entity = mapper.toEntity(dto);
        TipoDocumento saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    /**
     * Atualiza um tipo de documento existente ou lança 404 se não existir.
     */
    @Transactional
    public TipoDocumentoResponseDTO update(Integer id, TipoDocumentoRequestDTO dto) {
        TipoDocumento existing = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "TipoDocumento não encontrado com id " + id
                        )
                );
        mapper.updateFromDto(dto, existing);
        TipoDocumento saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    /**
     * Exclui um tipo de documento ou lança 404 se não existir.
     */
    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "TipoDocumento não encontrado com id " + id
            );
        }
        repository.deleteById(id);
    }
}