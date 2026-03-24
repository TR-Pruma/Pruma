package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.PermissaoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.PermissaoUsuarioResponseDTO;
import com.br.pruma.application.service.PermissaoUsuarioService;
import com.br.pruma.core.domain.PermissaoUsuario;
import com.br.pruma.core.repository.PermissaoUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissaoUsuarioServiceImpl implements PermissaoUsuarioService {

    private final PermissaoUsuarioRepository permissaoUsuarioRepository;

    @Override
    @Transactional
    public PermissaoUsuarioResponseDTO criar(PermissaoUsuarioRequestDTO request) {
        PermissaoUsuario entity = PermissaoUsuario.builder()
                .permissao(request.permissao())
                .build();
        return toResponse(permissaoUsuarioRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissaoUsuarioResponseDTO> listarTodos() {
        return permissaoUsuarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PermissaoUsuarioResponseDTO buscarPorId(Integer id) {
        return permissaoUsuarioRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("PermissaoUsuario não encontrada: " + id));
    }

    @Override
    @Transactional
    public PermissaoUsuarioResponseDTO atualizar(Integer id, PermissaoUsuarioRequestDTO request) {
        PermissaoUsuario entity = permissaoUsuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PermissaoUsuario não encontrada: " + id));
        entity.setPermissao(request.permissao());
        return toResponse(permissaoUsuarioRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!permissaoUsuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("PermissaoUsuario não encontrada: " + id);
        }
        permissaoUsuarioRepository.deleteById(id);
    }

    private PermissaoUsuarioResponseDTO toResponse(PermissaoUsuario e) {
        return new PermissaoUsuarioResponseDTO(
                e.getId(),
                e.getPermissao(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
