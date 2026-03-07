package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.TipoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.TipoUsuarioResponseDTO;
import com.br.pruma.application.dto.update.TipoUsuarioUpdateDTO;
import com.br.pruma.application.mapper.TipoUsuarioMapper;
import com.br.pruma.core.domain.TipoUsuario;
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
public class TipoUsuarioService {

    private final TipoUsuarioRepository repository;
    private final TipoUsuarioMapper mapper;

    public TipoUsuarioResponseDTO create(TipoUsuarioRequestDTO dto) {
        TipoUsuario entity = mapper.toEntity(dto);
        TipoUsuario saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public TipoUsuarioResponseDTO getById(Integer id) {
        TipoUsuario entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de usuário não encontrado: " + id));
        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<TipoUsuarioResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public TipoUsuarioResponseDTO update(Integer id, TipoUsuarioUpdateDTO dto) {
        TipoUsuario entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de usuário não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        TipoUsuario updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Tipo de usuário não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
