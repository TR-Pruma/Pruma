package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.PermissaoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.PermissaoUsuarioResponseDTO;
import com.br.pruma.application.dto.update.PermissaoUsuarioUpdateDTO;
import com.br.pruma.application.mapper.PermissaoUsuarioMapper;
import com.br.pruma.application.service.PermissaoUsuarioService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.PermissaoUsuario;
import com.br.pruma.core.repository.PermissaoUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissaoUsuarioServiceImpl implements PermissaoUsuarioService {

    private final PermissaoUsuarioRepository repository;
    private final PermissaoUsuarioMapper mapper;

    @Override
    public PermissaoUsuarioResponseDTO create(PermissaoUsuarioRequestDTO dto) {
        PermissaoUsuario entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public PermissaoUsuarioResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "PermissaoUsuario com ID " + id + " não encontrada.")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissaoUsuarioResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissaoUsuarioResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissaoUsuarioResponseDTO> listByUsuario(Integer usuarioId) {
        return repository.findAllByUsuario_Id(usuarioId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public PermissaoUsuarioResponseDTO update(Integer id, PermissaoUsuarioUpdateDTO dto) {
        PermissaoUsuario entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "PermissaoUsuario com ID " + id + " não encontrada."));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        PermissaoUsuario entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "PermissaoUsuario com ID " + id + " não encontrada."));
        repository.delete(entity);
    }
}
