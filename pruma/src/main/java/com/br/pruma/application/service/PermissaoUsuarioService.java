package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.PermissaoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.PermissaoUsuarioResponseDTO;
import com.br.pruma.application.mapper.PermissaoUsuarioMapper;
import com.br.pruma.core.domain.PermissaoUsuario;
import com.br.pruma.core.repository.PermissaoUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissaoUsuarioService {

    private final PermissaoUsuarioRepository repository;
    private final PermissaoUsuarioMapper mapper;

    @Transactional
    public PermissaoUsuarioResponseDTO criar(PermissaoUsuarioRequestDTO dto) {
        PermissaoUsuario entity = mapper.toEntity(dto);
        PermissaoUsuario salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public PermissaoUsuarioResponseDTO buscarPorId(Long id) {
        PermissaoUsuario entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permissão não encontrada para id: " + id));
        return mapper.toResponseDTO(entity);
    }

    @Transactional
    public PermissaoUsuarioResponseDTO atualizar(Long id, PermissaoUsuarioRequestDTO dto) {
        PermissaoUsuario existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permissão não encontrada para id: " + id));

        PermissaoUsuario atualizado = mapper.toEntity(dto);
        atualizado.setId(existente.getId());

        PermissaoUsuario salvo = repository.save(atualizado);
        return mapper.toResponseDTO(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Permissão não encontrada para id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PermissaoUsuarioResponseDTO> buscarPorCliente(String cpf) {
        return repository.findByCliente_Cpf(cpf)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PermissaoUsuarioResponseDTO> buscarPorTipoUsuario(Integer tipoUsuarioId) {
        return repository.findByTipoUsuario_Id(tipoUsuarioId)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }
}
