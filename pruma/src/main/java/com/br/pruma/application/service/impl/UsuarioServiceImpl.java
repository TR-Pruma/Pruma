package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.UsuarioRequestDTO;
import com.br.pruma.application.dto.response.UsuarioResponseDTO;
import com.br.pruma.application.dto.update.UsuarioUpdateDTO;
import com.br.pruma.application.mapper.UsuarioMapper;
import com.br.pruma.application.service.UsuarioService;
import com.br.pruma.core.domain.Usuario;
import com.br.pruma.core.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    @Override
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        Usuario entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public UsuarioResponseDTO update(Integer id, UsuarioUpdateDTO dto) {
        Usuario entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
