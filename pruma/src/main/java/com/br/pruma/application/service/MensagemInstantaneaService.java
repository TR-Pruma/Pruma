package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.MensagemInstantaneaRequestDTO;
import com.br.pruma.application.dto.response.MensagemInstantaneaResponseDTO;
import com.br.pruma.application.dto.update.MensagemInstantaneaUpdateDTO;
import com.br.pruma.application.mapper.MensagemInstantaneaMapper;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.MensagemInstantanea;
import com.br.pruma.core.domain.TipoUsuario;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.MensagemInstantaneaRepository;
import com.br.pruma.core.repository.TipoUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MensagemInstantaneaService {

    private final MensagemInstantaneaRepository repository;
    private final ClienteRepository clienteRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final MensagemInstantaneaMapper mapper;

    public MensagemInstantaneaResponseDTO create(MensagemInstantaneaRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(Math.toIntExact(dto.getClienteCpf()))
                .orElseThrow(() ->
                        new EntityNotFoundException("Cliente não encontrado: " + dto.getClienteCpf())
                );
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(dto.getTipoUsuarioId())
                .orElseThrow(() ->
                        new EntityNotFoundException("TipoUsuario não encontrado: " + dto.getTipoUsuarioId())
                );

        MensagemInstantanea entity = mapper.toEntity(dto);
        entity.setCliente(cliente);
        entity.setTipoUsuario(tipoUsuario);

        MensagemInstantanea saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public MensagemInstantaneaResponseDTO getById(Integer id) {
        MensagemInstantanea entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("MensagemInstantanea não encontrada: " + id)
                );
        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<MensagemInstantaneaResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MensagemInstantaneaResponseDTO> listByCliente(Long clienteCpf) {
        return repository.findAllByCliente_Cpf(clienteCpf).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MensagemInstantaneaResponseDTO> listByTipoUsuario(Integer tipoUsuarioId) {
        return repository.findAllByTipoUsuario_Id(tipoUsuarioId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MensagemInstantaneaResponseDTO> listByDestinatario(String destinatarioId) {
        return repository.findAllByDestinatarioId(destinatarioId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public MensagemInstantaneaResponseDTO update(Integer id, MensagemInstantaneaUpdateDTO dto) {
        MensagemInstantanea entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("MensagemInstantanea não encontrada: " + id)
                );

        if (dto.getClienteCpf() != null) {
            Cliente cliente = clienteRepository.findById(Math.toIntExact(dto.getClienteCpf()))
                    .orElseThrow(() ->
                            new EntityNotFoundException("Cliente não encontrado: " + dto.getClienteCpf())
                    );
            entity.setCliente(cliente);
        }
        if (dto.getTipoUsuarioId() != null) {
            TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(dto.getTipoUsuarioId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("TipoUsuario não encontrado: " + dto.getTipoUsuarioId())
                    );
            entity.setTipoUsuario(tipoUsuario);
        }

        mapper.updateFromDto(dto, entity);
        MensagemInstantanea updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("MensagemInstantanea não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}