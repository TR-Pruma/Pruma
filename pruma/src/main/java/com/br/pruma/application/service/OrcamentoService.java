package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.OrcamentoRequestDTO;
import com.br.pruma.application.dto.response.OrcamentoResponseDTO;
import com.br.pruma.application.dto.update.OrcamentoUpdateDTO;
import com.br.pruma.application.mapper.OrcamentoMapper;
import com.br.pruma.core.domain.Empresa;
import com.br.pruma.core.domain.Orcamento;
import com.br.pruma.core.domain.Projeto;
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

    private final OrcamentoRepository repository;
    private final ProjetoRepository projetoRepository;
    private final EmpresaRepository empresaRepository;
    private final OrcamentoMapper mapper;

    /**
     * Cria um novo orçamento, vinculando-o a um projeto e a uma empresa existentes.
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

        Orcamento saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Recupera os dados de um orçamento pelo seu ID.
     */
    @Transactional(readOnly = true)
    public OrcamentoResponseDTO getById(Integer id) {
        Orcamento entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Orçamento não encontrado: " + id)
                );
        return mapper.toResponse(entity);
    }

    /**
     * Lista todos os orçamentos cadastrados.
     */
    @Transactional(readOnly = true)
    public List<OrcamentoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos os orçamentos de um projeto específico.
     */
    @Transactional(readOnly = true)
    public List<OrcamentoResponseDTO> listByProjeto(Integer projetoId) {
        return repository.findByProjeto_Id(projetoId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza campos de um orçamento existente.
     */
    public OrcamentoResponseDTO update(Integer id, OrcamentoUpdateDTO dto) {
        Orcamento entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Orçamento não encontrado: " + id)
                );

        mapper.updateFromDto(dto, entity);
        Orcamento updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Remove (hard delete) um orçamento pelo seu ID.
     */
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Orçamento não encontrado: " + id);
        }
        repository.deleteById(id);
    }

}
