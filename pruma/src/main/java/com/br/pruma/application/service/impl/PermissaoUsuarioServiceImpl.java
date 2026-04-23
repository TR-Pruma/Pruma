package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.PermissaoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.PermissaoUsuarioResponseDTO;
import com.br.pruma.application.dto.update.PermissaoUsuarioUpdateDTO;
import com.br.pruma.application.mapper.PermissaoUsuarioMapper;
import com.br.pruma.application.service.PermissaoUsuarioService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.PermissaoUsuario;
import com.br.pruma.core.domain.TipoUsuario;
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
        return mapper.toResponseDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public PermissaoUsuarioResponseDTO getById(Long id) {
        return mapper.toResponseDTO(repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "PermissaoUsuario com ID " + id + " não encontrada.")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissaoUsuarioResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissaoUsuarioResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissaoUsuarioResponseDTO> listByClienteCpf(String cpf) {
        return repository.findByCliente_Cpf(cpf).stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public PermissaoUsuarioResponseDTO update(Long id, PermissaoUsuarioUpdateDTO dto) {
        PermissaoUsuario entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "PermissaoUsuario com ID " + id + " não encontrada."));
        if (dto.permissao() != null) {
            entity.setPermissao(dto.permissao());
        }
        if (dto.tipoUsuarioId() != null) {
            entity.setTipoUsuario(TipoUsuario.builder().id(dto.tipoUsuarioId()).build());
        }
        return mapper.toResponseDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        PermissaoUsuario entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "PermissaoUsuario com ID " + id + " não encontrada."));
        entity.setAtivo(false);
        repository.save(entity);
    }
}
