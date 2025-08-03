package com.br.pruma.infra.impl;

import com.br.pruma.application.dto.request.ClienteTipoRequestDTO;
import com.br.pruma.application.dto.response.ClienteTipoResponseDTO;
import com.br.pruma.application.mapper.ClienteTipoMapper;
import com.br.pruma.infra.repository.ClienteTipoService;
import com.br.pruma.core.domain.ClienteTipo;
import com.br.pruma.core.domain.TipoUsuario;
import com.br.pruma.core.repository.ClienteTipoRepository;
import com.br.pruma.core.repository.TipoUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteTipoServiceImpl implements ClienteTipoService {

    private final ClienteTipoRepository clienteTipoRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final ClienteTipoMapper clienteTipoMapper;

    @Override
    @Transactional
    public ClienteTipoResponseDTO criar(ClienteTipoRequestDTO request) {
        if (clienteTipoRepository.existsByDescricaoClienteAndAtivoTrue(request.getDescricaoCliente())) {
            throw new IllegalArgumentException("Já existe um tipo de cliente com esta descrição");
        }

        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(request.getTipoUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de usuário não encontrado"));

        ClienteTipo clienteTipo = clienteTipoMapper.toEntity(request, tipoUsuario);
        clienteTipo = clienteTipoRepository.save(clienteTipo);

        return clienteTipoMapper.toDTO(clienteTipo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteTipoResponseDTO> listarTodos() {
        return clienteTipoRepository.findByAtivoTrue()
                .stream()
                .map(clienteTipoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteTipoResponseDTO buscarPorId(Integer id) {
        ClienteTipo clienteTipo = clienteTipoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("ClienteTipo não encontrado"));

        return clienteTipoMapper.toDTO(clienteTipo);
    }

    @Override
    @Transactional
    public ClienteTipoResponseDTO atualizar(Integer id, ClienteTipoRequestDTO request) {
        ClienteTipo clienteTipo = clienteTipoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("ClienteTipo não encontrado"));

        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(request.getTipoUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de usuário não encontrado"));

        clienteTipo.setTipoUsuario(tipoUsuario);
        clienteTipo.setDescricaoCliente(request.getDescricaoCliente());
        clienteTipo = clienteTipoRepository.save(clienteTipo);

        return clienteTipoMapper.toDTO(clienteTipo);
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        ClienteTipo clienteTipo = clienteTipoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("ClienteTipo não encontrado"));

        clienteTipo.setAtivo(false);
        clienteTipoRepository.save(clienteTipo);
    }
}