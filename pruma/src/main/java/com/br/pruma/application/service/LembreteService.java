package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.LembreteRequestDTO;
import com.br.pruma.application.dto.response.LembreteResponseDTO;
import com.br.pruma.application.dto.update.LembreteUpdateDTO;
import com.br.pruma.application.mapper.LembreteMapper;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Lembrete;
import com.br.pruma.core.domain.TipoUsuario;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.LembreteRepository;
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
public class LembreteService {

    private final LembreteRepository lembreteRepo;
    private final ClienteRepository clienteRepo;
    private final TipoUsuarioRepository tipoUsuarioRepo;
    private final LembreteMapper mapper;

    /**
     * Cria um novo lembrete para um cliente e um tipo de usuário existentes.
     */
    public LembreteResponseDTO create(LembreteRequestDTO dto) {
        Cliente cliente = clienteRepo.findById(Integer.valueOf(dto.getClienteCpf()))
                .orElseThrow(() ->
                        new EntityNotFoundException("Cliente não encontrado: " + dto.getClienteCpf())
                );
        TipoUsuario tipoUsuario = tipoUsuarioRepo.findById(dto.getTipoUsuarioId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Tipo de usuário não encontrado: " + dto.getTipoUsuarioId())
                );

        Lembrete entity = mapper.toEntity(dto);
        entity.setCliente(cliente);
        entity.setTipoUsuario(tipoUsuario);

        Lembrete saved = lembreteRepo.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Busca um lembrete por seu ID.
     */
    @Transactional(readOnly = true)
    public LembreteResponseDTO getById(Integer id) {
        Lembrete entity = lembreteRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Lembrete não encontrado: " + id)
                );
        return mapper.toResponse(entity);
    }

    /**
     * Lista todos os lembretes.
     */
    @Transactional(readOnly = true)
    public List<LembreteResponseDTO> listAll() {
        return lembreteRepo.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista lembretes de um cliente específico.
     */
    @Transactional(readOnly = true)
    public List<LembreteResponseDTO> listByCliente(String clienteCpf) {
        return lembreteRepo.findByCliente_CpfOrderByDataHora(clienteCpf).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista lembretes para um determinado tipo de usuário.
     */
    @Transactional(readOnly = true)
    public List<LembreteResponseDTO> listByTipoUsuario(Integer tipoUsuarioId) {
        return lembreteRepo.findByTipoUsuario_IdOrderByDataHora(tipoUsuarioId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza parcialmente um lembrete existente.
     */
    public LembreteResponseDTO update(Integer id, LembreteUpdateDTO dto) {
        Lembrete entity = lembreteRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Lembrete não encontrado: " + id)
                );
        mapper.updateFromDto(dto, entity);
        Lembrete updated = lembreteRepo.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Remove fisicamente um lembrete pelo ID.
     */
    public void delete(Integer id) {
        if (!lembreteRepo.existsById(id)) {
            throw new EntityNotFoundException("Lembrete não encontrado: " + id);
        }
        lembreteRepo.deleteById(id);
    }


}
