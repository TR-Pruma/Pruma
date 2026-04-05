package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ClienteTipoRequestDTO;
import com.br.pruma.application.dto.response.ClienteTipoResponseDTO;
import com.br.pruma.application.mapper.ClienteTipoMapper;
import com.br.pruma.application.service.ClienteTipoService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.ClienteTipo;
import com.br.pruma.core.domain.TipoUsuario;
import com.br.pruma.core.repository.ClienteTipoRepository;
import com.br.pruma.core.repository.TipoUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteTipoServiceImpl implements ClienteTipoService {

    private final ClienteTipoRepository repository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final ClienteTipoMapper mapper;

    @Override
    public ClienteTipoResponseDTO criar(ClienteTipoRequestDTO request) {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(request.getTipoUsuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "TipoUsuario com ID " + request.getTipoUsuarioId() + " não encontrado."));
        ClienteTipo entity = mapper.toEntity(request, tipoUsuario);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteTipoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteTipoResponseDTO buscarPorId(Integer id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "ClienteTipo com ID " + id + " não encontrado."));
    }

    @Override
    public ClienteTipoResponseDTO atualizar(Integer id, ClienteTipoRequestDTO request) {
        repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "ClienteTipo com ID " + id + " não encontrado."));
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(request.getTipoUsuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "TipoUsuario com ID " + request.getTipoUsuarioId() + " não encontrado."));
        ClienteTipo entity = mapper.toEntity(request, tipoUsuario);
        entity.setId(id);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void deletar(Integer id) {
        ClienteTipo entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "ClienteTipo com ID " + id + " não encontrado."));
        repository.delete(entity);
    }
}
