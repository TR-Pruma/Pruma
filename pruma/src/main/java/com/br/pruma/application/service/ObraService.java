package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ObraRequestDTO;
import com.br.pruma.application.dto.response.ObraResponseDTO;
import com.br.pruma.application.dto.update.ObraUpdateDTO;
import com.br.pruma.application.mapper.ObraMapper;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ObraRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ObraService {

    private final ObraRepository obraRepository;
    private final ProjetoRepository projetoRepository;
    private final ObraMapper mapper;

    /**
     * Cria uma nova Obra.
     */
    public ObraResponseDTO create(ObraRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));

        Obra entity = mapper.toEntity(dto);
        entity.setProjeto(projeto);

        Obra saved = obraRepository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Recupera uma Obra por id.
     */
    @Transactional(readOnly = true)
    public ObraResponseDTO getById(Integer id) {
        Obra entity = obraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + id));
        return mapper.toResponse(entity);
    }

    /**
     * Lista todas as Obras (sem paginação).
     * Para conjunto grande, prefira listar(Pageable).
     */
    @Transactional(readOnly = true)
    public List<ObraResponseDTO> listAll() {
        return obraRepository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista Obras com paginação e ordenação.
     */
    @Transactional(readOnly = true)
    public Page<ObraResponseDTO> list(Pageable pageable) {
        return obraRepository.findAll(pageable).map(mapper::toResponse);
    }

    /**
     * Lista Obras por projeto.
     */
    @Transactional(readOnly = true)
    public List<ObraResponseDTO> listByProjeto(Integer projetoId) {
        return obraRepository.findAllByProjeto_Id(projetoId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualização parcial da Obra.
     */
    public ObraResponseDTO update(Integer id, ObraUpdateDTO dto) {
        Obra entity = obraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + id));

        if (dto.getProjetoId() != null) {
            Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                    .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
            entity.setProjeto(projeto);
        }

        mapper.updateFromDto(dto, entity);
        Obra updated = obraRepository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Substituição completa (PUT) — sobrescreve campos permitidos.
     */
    public ObraResponseDTO replace(Integer id, ObraRequestDTO dto) {
        Obra existing = obraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + id));

        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));

        Obra updated = mapper.toEntity(dto);
        updated.setId(existing.getId());
        updated.setProjeto(projeto);
        // preserve audit/version handled by JPA
        Obra saved = obraRepository.save(updated);
        return mapper.toResponse(saved);
    }

    /**
     * Remove permanentemente uma Obra.
     * Para soft-delete, adapte para marcar flag em vez de remover.
     */
    public void delete(Integer id) {
        if (!obraRepository.existsById(id)) {
            throw new EntityNotFoundException("Obra não encontrada: " + id);
        }
        obraRepository.deleteById(id);
    }

    /**
     * Busca por descrição com paginação (exemplo de filtro).
     */
    @Transactional(readOnly = true)
    public Page<ObraResponseDTO> searchByDescricao(String descricao, Pageable pageable) {
        List<Obra> found = obraRepository.findAllByDescricaoContainingIgnoreCase(descricao);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), found.size());
        List<ObraResponseDTO> content = found.subList(start, end).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, found.size());
    }
}