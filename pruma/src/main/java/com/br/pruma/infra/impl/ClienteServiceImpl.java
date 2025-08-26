package com.br.pruma.infra.impl;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.application.mapper.ClienteMapper;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.repository.ClienteRepository;

import com.br.pruma.infra.repository.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;


    @Override
    public ClienteResponseDTO create(ClienteRequestDTO dto) {
        Cliente cliente = mapper.toEntity(dto);
        Cliente saved = repository.save(cliente);
        return mapper.toDto(saved);
    }

    @Override
    public ClienteResponseDTO update(Integer id, ClienteRequestDTO dto) {
        Cliente existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + id));
        mapper.updateFromDto(dto, existing);
        Cliente updated = repository.save(existing);
        return mapper.toDto(updated);
    }
    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO findById(Integer id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + id));
        return mapper.toDto(cliente);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado: " + id);
        }
        repository.deleteById(id);
    }


}
