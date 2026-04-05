package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.OrcamentoRequestDTO;
import com.br.pruma.application.dto.response.OrcamentoResponseDTO;
import com.br.pruma.application.dto.update.OrcamentoUpdateDTO;
import com.br.pruma.application.mapper.OrcamentoMapper;
import com.br.pruma.application.service.OrcamentoService;
import com.br.pruma.core.domain.Empresa;
import com.br.pruma.core.domain.Orcamento;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.EmpresaRepository;
import com.br.pruma.core.repository.OrcamentoRepository;
import com.br.pruma.core.repository.ProjetoRepository;
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
public class OrcamentoServiceImpl implements OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final ProjetoRepository projetoRepository;
    private final EmpresaRepository empresaRepository;
    private final OrcamentoMapper mapper;

    @Override
    public OrcamentoResponseDTO create(OrcamentoRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
        Empresa empresa = empresaRepository.findById(dto.getEmpresaCnpj())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada: " + dto.getEmpresaCnpj()));
        Orcamento entity = mapper.toEntity(dto);
        entity.setProjeto(projeto);
        entity.setEmpresa(empresa);
        return mapper.toResponse(orcamentoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public OrcamentoResponseDTO getById(Integer id) {
        return mapper.toResponse(orcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orcamento não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrcamentoResponseDTO> listAll() {
        return orcamentoRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrcamentoResponseDTO> list(Pageable pageable) {
        return orcamentoRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrcamentoResponseDTO> listByProjeto(Integer projetoId) {
        return orcamentoRepository.findAll().stream()
                .filter(o -> o.getProjeto() != null && o.getProjeto().getId().equals(projetoId))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public OrcamentoResponseDTO update(Integer id, OrcamentoUpdateDTO dto) {
        Orcamento entity = orcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orcamento não encontrado: " + id));
        if (dto.getProjetoId() != null) {
            Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                    .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
            entity.setProjeto(projeto);
        }
        if (dto.getEmpresaCnpj() != null) {
            Empresa empresa = empresaRepository.findById(dto.getEmpresaCnpj())
                    .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada: " + dto.getEmpresaCnpj()));
            entity.setEmpresa(empresa);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(orcamentoRepository.save(entity));
    }

    @Override
    public OrcamentoResponseDTO replace(Integer id, OrcamentoRequestDTO dto) {
        orcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orcamento não encontrado: " + id));
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
        Empresa empresa = empresaRepository.findById(dto.getEmpresaCnpj())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada: " + dto.getEmpresaCnpj()));
        Orcamento updated = mapper.toEntity(dto);
        updated.setId(id);
        updated.setProjeto(projeto);
        updated.setEmpresa(empresa);
        return mapper.toResponse(orcamentoRepository.save(updated));
    }

    @Override
    public void delete(Integer id) {
        Orcamento entity = orcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orcamento não encontrado: " + id));
        orcamentoRepository.delete(entity);
    }
}
