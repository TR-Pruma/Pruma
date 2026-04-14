package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.PosObraRequestDTO;
import com.br.pruma.application.dto.response.PosObraResponseDTO;
import com.br.pruma.application.dto.update.PosObraUpdateDTO;
import com.br.pruma.application.mapper.PosObraMapper;
import com.br.pruma.application.service.PosObraService;
import com.br.pruma.core.domain.PosObra;
import com.br.pruma.core.repository.port.PosObraRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PosObraServiceImpl implements PosObraService {

    private final PosObraRepositoryPort posObraRepositoryPort;
    private final PosObraMapper posObraMapper;

    @Override
    public PosObraResponseDTO create(PosObraRequestDTO dto) {
        PosObra posObra = posObraMapper.toEntity(dto);
        PosObra saved = posObraRepositoryPort.save(posObra);
        return posObraMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PosObraResponseDTO getById(Integer id) {
        PosObra posObra = posObraRepositoryPort.findById(Long.valueOf(id))
                .orElseThrow(() -> new EntityNotFoundException("PosObra não encontrada: " + id));
        return posObraMapper.toResponse(posObra);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PosObraResponseDTO> listAll() {
        return posObraRepositoryPort.findAll()
                .stream()
                .map(posObraMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PosObraResponseDTO> list(Pageable pageable) {
        return posObraRepositoryPort.findAll(pageable)
                .map(posObraMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PosObraResponseDTO> listByProjeto(Integer projetoId) {
        return posObraRepositoryPort.findAllByObra_Id(Long.valueOf(projetoId))
                .stream()
                .map(posObraMapper::toResponse)
                .toList();
    }

    @Override
    public PosObraResponseDTO update(Integer id, PosObraUpdateDTO dto) {
        PosObra posObra = posObraRepositoryPort.findById(Long.valueOf(id))
                .orElseThrow(() -> new EntityNotFoundException("PosObra não encontrada: " + id));
        posObraMapper.updateFromDto(dto, posObra);
        PosObra updated = posObraRepositoryPort.save(posObra);
        return posObraMapper.toResponse(updated);
    }

    @Override
    public PosObraResponseDTO replace(Integer id, PosObraRequestDTO dto) {
        posObraRepositoryPort.findById(Long.valueOf(id))
                .orElseThrow(() -> new EntityNotFoundException("PosObra não encontrada: " + id));
        PosObra posObra = posObraMapper.toEntity(dto);
        posObra.setId(Long.valueOf(id));
        PosObra replaced = posObraRepositoryPort.save(posObra);
        return posObraMapper.toResponse(replaced);
    }

    @Override
    public void delete(Integer id) {
        PosObra posObra = posObraRepositoryPort.findById(Long.valueOf(id))
                .orElseThrow(() -> new EntityNotFoundException("PosObra não encontrada: " + id));
        posObraRepositoryPort.delete(posObra);
    }
}
