package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.TipoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.TipoUsuarioResponseDTO;
import com.br.pruma.application.dto.update.TipoUsuarioUpdateDTO;
import com.br.pruma.application.mapper.TipoUsuarioMapper;
import com.br.pruma.application.service.TipoUsuarioService;
import com.br.pruma.core.domain.TipoUsuario;
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
public class TipoUsuarioServiceImpl implements TipoUsuarioService {

    private final TipoUsuarioRepository repository;
    private final TipoUsuarioMapper mapper;

    @Override
    public TipoUsuarioResponseDTO create(TipoUsuarioRequestDTO dto) {
        TipoUsuario entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public TipoUsuarioResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TipoUsuario não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoUsuarioResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoUsuarioResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public TipoUsuarioResponseDTO update(Integer id, TipoUsuarioUpdateDTO dto) {
        TipoUsuario entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TipoUsuario não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public TipoUsuarioResponseDTO replace(Integer id, TipoUsuarioRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TipoUsuario não encontrado: " + id));
        TipoUsuario entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("TipoUsuario não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
