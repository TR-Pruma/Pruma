package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.LembreteRequestDTO;
import com.br.pruma.application.dto.response.LembreteResponseDTO;
import com.br.pruma.application.dto.update.LembreteUpdateDTO;
import com.br.pruma.application.mapper.LembreteMapper;
import com.br.pruma.application.service.LembreteService;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Lembrete;
import com.br.pruma.core.domain.TipoUsuario;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.LembreteRepository;
import com.br.pruma.core.repository.TipoUsuarioRepository;
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
public class LembreteServiceImpl implements LembreteService {

    private final LembreteRepository lembreteRepo;
    private final ClienteRepository clienteRepo;
    private final TipoUsuarioRepository tipoUsuarioRepo;
    private final LembreteMapper mapper;

    @Override
    public LembreteResponseDTO create(LembreteRequestDTO dto) {
        Cliente cliente = clienteRepo.findById(Integer.parseInt(dto.getClienteCpf()))
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + dto.getClienteCpf()));
        TipoUsuario tipoUsuario = tipoUsuarioRepo.findById(dto.getTipoUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("TipoUsuario não encontrado: " + dto.getTipoUsuarioId()));
        Lembrete entity = mapper.toEntity(dto);
        entity.setCliente(cliente);
        entity.setTipoUsuario(tipoUsuario);
        return mapper.toResponse(lembreteRepo.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public LembreteResponseDTO getById(Integer id) {
        return mapper.toResponse(lembreteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lembrete não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LembreteResponseDTO> listAll() {
        return lembreteRepo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LembreteResponseDTO> list(Pageable pageable) {
        return lembreteRepo.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LembreteResponseDTO> listByProjeto(Integer projetoId) {
        return lembreteRepo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public LembreteResponseDTO update(Integer id, LembreteUpdateDTO dto) {
        Lembrete entity = lembreteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lembrete não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(lembreteRepo.save(entity));
    }

    @Override
    public LembreteResponseDTO replace(Integer id, LembreteRequestDTO dto) {
        lembreteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lembrete não encontrado: " + id));
        Lembrete entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(lembreteRepo.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!lembreteRepo.existsById(id)) {
            throw new EntityNotFoundException("Lembrete não encontrado: " + id);
        }
        lembreteRepo.deleteById(id);
    }
}
