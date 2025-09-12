package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.NotificacaoRequestDTO;
import com.br.pruma.application.dto.response.NotificacaoResponseDTO;
import com.br.pruma.application.dto.update.NotificacaoUpdateDTO;
import com.br.pruma.application.mapper.NotificacaoMapper;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Notificacao;
import com.br.pruma.core.domain.TipoUsuario;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.NotificacaoRepository;
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
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final ClienteRepository clienteRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final NotificacaoMapper mapper;

    /**
     * Cria uma nova notificação.
     */
    public NotificacaoResponseDTO create(NotificacaoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(Math.toIntExact(dto.getClienteCpf()))
                .orElseThrow(() ->
                        new EntityNotFoundException("Cliente não encontrado: " + dto.getClienteCpf())
                );
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(dto.getTipoUsuarioId())
                .orElseThrow(() ->
                        new EntityNotFoundException("TipoUsuario não encontrado: " + dto.getTipoUsuarioId())
                );

        Notificacao entity = mapper.toEntity(dto);
        entity.setCliente(cliente);
        entity.setTipoUsuario(tipoUsuario);

        Notificacao saved = notificacaoRepository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Recupera uma notificação pelo seu ID.
     */
    @Transactional(readOnly = true)
    public NotificacaoResponseDTO getById(Integer id) {
        Notificacao entity = notificacaoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Notificação não encontrada: " + id)
                );
        return mapper.toResponse(entity);
    }

    /**
     * Lista todas as notificações.
     */
    @Transactional(readOnly = true)
    public List<NotificacaoResponseDTO> listAll() {
        return notificacaoRepository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista notificações por cliente.
     */
    @Transactional(readOnly = true)
    public List<NotificacaoResponseDTO> listByCliente(Long clienteCpf) {
        return notificacaoRepository.findAllByCliente_Cpf(clienteCpf).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista notificações por tipo de usuário.
     */
    @Transactional(readOnly = true)
    public List<NotificacaoResponseDTO> listByTipoUsuario(Integer tipoUsuarioId) {
        return notificacaoRepository.findAllByTipoUsuario_Id(tipoUsuarioId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista notificações por status de leitura.
     */
    @Transactional(readOnly = true)
    public List<NotificacaoResponseDTO> listByLida(Boolean lida) {
        return notificacaoRepository.findAllByLida(lida).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza parcialmente uma notificação existente.
     */
    public NotificacaoResponseDTO update(Integer id, NotificacaoUpdateDTO dto) {
        Notificacao entity = notificacaoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Notificação não encontrada: " + id)
                );

        if (dto.getClienteCpf() != null) {
            Cliente cliente = clienteRepository.findById(Math.toIntExact(dto.getClienteCpf()))
                    .orElseThrow(() ->
                            new EntityNotFoundException("Cliente não encontrado: " + dto.getClienteCpf())
                    );
            entity.setCliente(cliente);
        }
        if (dto.getTipoUsuarioId() != null) {
            TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(dto.getTipoUsuarioId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("TipoUsuario não encontrado: " + dto.getTipoUsuarioId())
                    );
            entity.setTipoUsuario(tipoUsuario);
        }

        mapper.updateFromDto(dto, entity);
        Notificacao updated = notificacaoRepository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Exclui logicamente uma notificação por ID.
     */
    public void delete(Integer id) {
        if (!notificacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Notificação não encontrada: " + id);
        }
        notificacaoRepository.deleteById(id);
    }
}