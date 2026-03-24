package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.PosObraRequestDTO;
import com.br.pruma.application.dto.response.PosObraResponseDTO;
import com.br.pruma.application.dto.update.PosObraUpdateDTO;
import com.br.pruma.application.mapper.PosObraMapper;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.PosObra;
import com.br.pruma.core.repository.ObraRepository;
import com.br.pruma.core.repository.PosObraRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PosObraService {

    private final PosObraRepository posObraRepository;
    private final ObraRepository obraRepository;
    private final PosObraMapper mapper;

    /**
     * Cria um novo registro de Pós-Obra.
     */
    public PosObraResponseDTO create(PosObraRequestDTO dto) {
        Obra obra = obraRepository.findById(Math.toIntExact(dto.getObraId()))
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));

        PosObra entity = mapper.toEntity(dto);
        entity.setObra(obra);

        PosObra saved = posObraRepository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Recupera um registro de Pós-Obra por id.
     */
    @Transactional(readOnly = true)
    public PosObraResponseDTO getById(Long id) {
        PosObra entity = posObraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PosObra não encontrado: " + id));
        return mapper.toResponse(entity);
    }

    /**
     * Lista todos os registros de Pós-Obra.
     */
    @Transactional(readOnly = true)
    public List<PosObraResponseDTO> listAll() {
        return posObraRepository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos os registros de Pós-Obra associados a uma obra.
     */
    @Transactional(readOnly = true)
    public List<PosObraResponseDTO> listByObra(Long obraId) {
        return posObraRepository.findAllByObra_Id(obraId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza parcialmente um registro de Pós-Obra.
     */
    public PosObraResponseDTO update(Long id, PosObraUpdateDTO dto) {
        PosObra entity = posObraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PosObra não encontrado: " + id));

        if (dto.getObraId() != null) {
            Obra obra = obraRepository.findById(Math.toIntExact(dto.getObraId()))
                    .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
            entity.setObra(obra);
        }

        mapper.updateFromDto(dto, entity);
        PosObra updated = posObraRepository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Remove permanentemente um registro de Pós-Obra.
     * Se desejar soft-delete, adapte para marcar um flag em vez de deletar.
     */
    public void delete(Long id) {
        if (!posObraRepository.existsById(id)) {
            throw new EntityNotFoundException("Pós Obra não encontrado: " + id);
        }
        posObraRepository.deleteById(id);
    }
}