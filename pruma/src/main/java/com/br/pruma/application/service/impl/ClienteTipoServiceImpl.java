package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ClienteTipoRequestDTO;
import com.br.pruma.application.dto.response.ClienteTipoResponseDTO;
import com.br.pruma.application.service.ClienteTipoService;
import com.br.pruma.core.domain.ClienteTipo;
import com.br.pruma.core.repository.ClienteTipoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteTipoServiceImpl implements ClienteTipoService {

    private final ClienteTipoRepository clienteTipoRepository;

    @Override
    @Transactional
    public ClienteTipoResponseDTO criar(ClienteTipoRequestDTO request) {
        ClienteTipo entity = ClienteTipo.builder()
                .tipo(request.tipo())
                .build();
        ClienteTipo saved = clienteTipoRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteTipoResponseDTO> listarTodos() {
        return clienteTipoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteTipoResponseDTO buscarPorId(Integer id) {
        return clienteTipoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("ClienteTipo não encontrado: " + id));
    }

    @Override
    @Transactional
    public ClienteTipoResponseDTO atualizar(Integer id, ClienteTipoRequestDTO request) {
        ClienteTipo entity = clienteTipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ClienteTipo não encontrado: " + id));
        entity.setTipo(request.tipo());
        return toResponse(clienteTipoRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!clienteTipoRepository.existsById(id)) {
            throw new EntityNotFoundException("ClienteTipo não encontrado: " + id);
        }
        clienteTipoRepository.deleteById(id);
    }

    private ClienteTipoResponseDTO toResponse(ClienteTipo e) {
        return new ClienteTipoResponseDTO(
                e.getId(),
                e.getTipo(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
