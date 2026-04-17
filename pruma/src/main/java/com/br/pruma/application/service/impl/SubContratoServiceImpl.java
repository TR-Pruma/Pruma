package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.SubContratoRequestDTO;
import com.br.pruma.application.dto.response.SubContratoResponseDTO;
import com.br.pruma.application.dto.update.SubContratoUpdateDTO;
import com.br.pruma.application.mapper.SubContratoMapper;
import com.br.pruma.application.service.SubContratoService;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.domain.SubContrato;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import com.br.pruma.core.repository.SubContratoRepository;
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
public class SubContratoServiceImpl implements SubContratoService {

    private final SubContratoRepository repository;
    private final SubContratoMapper mapper;
    private final ClienteRepository clienteRepository;
    private final ProjetoRepository projetoRepository;

    @Override
    public SubContratoResponseDTO create(SubContratoRequestDTO dto) {
        SubContrato entity = mapper.toEntity(dto);

        Cliente cliente = clienteRepository.findByCpf(dto.clienteCpf())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cliente não encontrado: " + dto.clienteCpf()));

        Projeto projeto = projetoRepository.findById(dto.projetoId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Projeto não encontrado: " + dto.projetoId()));

        entity.setCliente(cliente);
        entity.setProjeto(projeto);

        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public SubContratoResponseDTO getById(Integer id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "SubContrato não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubContratoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubContratoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubContratoResponseDTO> listByProjeto(Integer projetoId) {
        return repository.findByProjetoId(projetoId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public SubContratoResponseDTO update(Integer id, SubContratoUpdateDTO dto) {
        SubContrato entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "SubContrato não encontrado: " + id));
        if (dto.clienteCpf() != null) {
            Cliente cliente = clienteRepository.findByCpf(dto.clienteCpf())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Cliente não encontrado: " + dto.clienteCpf()));
            entity.setCliente(cliente);
        }

        if (dto.projetoId() != null) {
            Projeto projeto = projetoRepository.findById(dto.projetoId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Projeto não encontrado: " + dto.projetoId()));
            entity.setProjeto(projeto);
        }

        mapper.updateFromDto(dto, entity);

        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public SubContratoResponseDTO replace(Integer id, SubContratoRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "SubContrato não encontrado: " + id));

        SubContrato entity = mapper.toEntity(dto);

        Cliente cliente = clienteRepository.findByCpf(dto.clienteCpf())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cliente não encontrado: " + dto.clienteCpf()));

        Projeto projeto = projetoRepository.findById(dto.projetoId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Projeto não encontrado: " + dto.projetoId()));

        entity.setId(id);
        entity.setCliente(cliente);
        entity.setProjeto(projeto);

        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        SubContrato entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "SubContrato não encontrado: " + id));
        repository.delete(entity);
    }
}