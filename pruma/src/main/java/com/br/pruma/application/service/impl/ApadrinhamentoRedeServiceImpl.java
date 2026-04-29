package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ApadrinhamentoRedeRequestDTO;
import com.br.pruma.application.dto.response.ApadrinhamentoRedeResponseDTO;
import com.br.pruma.application.dto.update.ApadrinhamentoRedeUpdateDTO;
import com.br.pruma.application.mapper.ApadrinhamentoRedeMapper;
import com.br.pruma.application.service.ApadrinhamentoRedeService;
import com.br.pruma.config.Constantes;
import com.br.pruma.core.domain.ApadrinhamentoRede;
import com.br.pruma.core.domain.ProfissionalDeBase;
import com.br.pruma.core.repository.port.ApadrinhamentoRedeRepositoryPort;
import com.br.pruma.core.repository.port.ProfissionalDeBaseRepositoryPort;
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
public class ApadrinhamentoRedeServiceImpl implements ApadrinhamentoRedeService {

    private final ApadrinhamentoRedeRepositoryPort apadrinhamentoRedeRepositoryPort;
    private final ProfissionalDeBaseRepositoryPort profissionalDeBaseRepositoryPort;
    private final ApadrinhamentoRedeMapper apadrinhamentoRedeMapper;

    @Override
    public ApadrinhamentoRedeResponseDTO create(ApadrinhamentoRedeRequestDTO dto) {
        ProfissionalDeBase padrinho = profissionalDeBaseRepositoryPort.findById(dto.padrinhoId().intValue())
                .orElseThrow(() -> new EntityNotFoundException(Constantes.PROFISSIONAL_DE_BASE_NAO_ENCONTRADO + dto.padrinhoId()));

        ProfissionalDeBase afilhado = profissionalDeBaseRepositoryPort.findById(dto.afilhadoId().intValue())
                .orElseThrow(() -> new EntityNotFoundException(Constantes.PROFISSIONAL_DE_BASE_NAO_ENCONTRADO + dto.afilhadoId()));

        if (apadrinhamentoRedeRepositoryPort.existsByPadrinhoIdAndAfilhadoIdAndStatus(
                dto.padrinhoId(), dto.afilhadoId(), "ATIVO")) {
            throw new IllegalStateException(Constantes.APADRINHAMENTO_REDE_VINCULO_JA_EXISTE);
        }

        ApadrinhamentoRede entity = apadrinhamentoRedeMapper.toEntity(dto);
        entity.setPadrinho(padrinho);
        entity.setAfilhado(afilhado);

        ApadrinhamentoRede saved = apadrinhamentoRedeRepositoryPort.save(entity);
        return apadrinhamentoRedeMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ApadrinhamentoRedeResponseDTO getById(Long id) {
        ApadrinhamentoRede entity = apadrinhamentoRedeRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.APADRINHAMENTO_REDE_NAO_ENCONTRADO + id));
        return apadrinhamentoRedeMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApadrinhamentoRedeResponseDTO> listAll() {
        return apadrinhamentoRedeRepositoryPort.findAll()
                .stream()
                .map(apadrinhamentoRedeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApadrinhamentoRedeResponseDTO> list(Pageable pageable) {
        return apadrinhamentoRedeRepositoryPort.findAll(pageable)
                .map(apadrinhamentoRedeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApadrinhamentoRedeResponseDTO> listByPadrinho(Long padrinhoId) {
        profissionalDeBaseRepositoryPort.findById(padrinhoId.intValue())
                .orElseThrow(() -> new EntityNotFoundException(Constantes.PROFISSIONAL_DE_BASE_NAO_ENCONTRADO + padrinhoId));
        return apadrinhamentoRedeRepositoryPort.findAllByPadrinhoId(padrinhoId)
                .stream()
                .map(apadrinhamentoRedeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApadrinhamentoRedeResponseDTO> listByAfilhado(Long afilhadoId) {
        profissionalDeBaseRepositoryPort.findById(afilhadoId.intValue())
                .orElseThrow(() -> new EntityNotFoundException(Constantes.PROFISSIONAL_DE_BASE_NAO_ENCONTRADO + afilhadoId));
        return apadrinhamentoRedeRepositoryPort.findAllByAfilhadoId(afilhadoId)
                .stream()
                .map(apadrinhamentoRedeMapper::toResponse)
                .toList();
    }

    @Override
    public ApadrinhamentoRedeResponseDTO update(Long id, ApadrinhamentoRedeUpdateDTO dto) {
        ApadrinhamentoRede entity = apadrinhamentoRedeRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.APADRINHAMENTO_REDE_NAO_ENCONTRADO + id));
        apadrinhamentoRedeMapper.updateFromDto(dto, entity);
        ApadrinhamentoRede updated = apadrinhamentoRedeRepositoryPort.save(entity);
        return apadrinhamentoRedeMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!apadrinhamentoRedeRepositoryPort.existsById(id)) {
            throw new EntityNotFoundException(Constantes.APADRINHAMENTO_REDE_NAO_ENCONTRADO + id);
        }
        apadrinhamentoRedeRepositoryPort.deleteById(id);
    }
}
