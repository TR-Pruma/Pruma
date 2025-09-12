package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.OrcamentoRequestDTO;
import com.br.pruma.application.dto.response.OrcamentoResponseDTO;
import com.br.pruma.application.dto.update.OrcamentoUpdateDTO;
import com.br.pruma.application.mapper.OrcamentoMapper;
import com.br.pruma.core.domain.Empresa;
import com.br.pruma.core.domain.Orcamento;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.enums.StatusOrcamento;
import com.br.pruma.core.repository.EmpresaRepository;
import com.br.pruma.core.repository.OrcamentoRepository;
import com.br.pruma.infra.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final ProjetoRepository projetoRepository;
    private final EmpresaRepository empresaRepository;
    private final OrcamentoMapper mapper;

    /**
     * Cria um novo orçamento.
     */
    public OrcamentoResponseDTO create(OrcamentoRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId())
                );
        Empresa empresa = empresaRepository.findById(dto.getEmpresaCnpj())
                .orElseThrow(() ->
                        new EntityNotFoundException("Empresa não encontrada: " + dto.getEmpresaCnpj())
                );

        Orcamento entity = mapper.toEntity(dto);
        entity.setProjeto(projeto);
        entity.setEmpresa(empresa);

        Orcamento saved = orcamentoRepository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Recupera orçamento pelo ID.
     */
    @Transactional(readOnly = true)
    public OrcamentoResponseDTO getById(Integer id) {
        Orcamento entity = orcamentoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Orçamento não encontrado: " + id)
                );
        return mapper.toResponse(entity);
    }

    /**
     * Lista todos os orçamentos.
     */
    @Transactional(readOnly = true)
    public List<OrcamentoResponseDTO> listAll() {
        return orcamentoRepository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista orçamentos por projeto.
     */
    @Transactional(readOnly = true)
    public List<OrcamentoResponseDTO> listByProjeto(Integer projetoId) {
        return orcamentoRepository.findAllByProjeto_Id(projetoId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista orçamentos por empresa (CNPJ).
     */
    @Transactional(readOnly = true)
    public List<OrcamentoResponseDTO> listByEmpresa(String empresaCnpj) {
        return orcamentoRepository.findAllByEmpresa_Cnpj(empresaCnpj).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista orçamentos por status.
     */
    @Transactional(readOnly = true)
    public List<OrcamentoResponseDTO> listByStatus(String statusStr) {
        StatusOrcamento status = StatusOrcamento.valueOf(statusStr);
        return orcamentoRepository.findAllByStatus(status).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza parcialmente um orçamento existente.
     */
    public OrcamentoResponseDTO update(Integer id, OrcamentoUpdateDTO dto) {
        Orcamento entity = orcamentoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Orçamento não encontrado: " + id)
                );

        if (dto.getProjetoId() != null) {
            Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId())
                    );
            entity.setProjeto(projeto);
        }
        if (dto.getEmpresaCnpj() != null) {
            Empresa empresa = empresaRepository.findById(dto.getEmpresaCnpj())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Empresa não encontrada: " + dto.getEmpresaCnpj())
                    );
            entity.setEmpresa(empresa);
        }

        mapper.updateFromDto(dto, entity);
        Orcamento updated = orcamentoRepository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Exclui logicamente um orçamento por ID.
     */
    public void delete(Integer id) {
        if (!orcamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Orçamento não encontrado: " + id);
        }
        orcamentoRepository.deleteById(id);
    }
}
