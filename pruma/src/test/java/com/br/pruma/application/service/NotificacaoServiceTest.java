package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.NotificacaoRequestDTO;
import com.br.pruma.application.dto.response.NotificacaoResponseDTO;
import com.br.pruma.application.mapper.NotificacaoMapper;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Notificacao;
import com.br.pruma.core.domain.TipoUsuario;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.NotificacaoRepository;
import com.br.pruma.core.repository.TipoUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacaoServiceTest {

    @Mock NotificacaoRepository notificacaoRepository;
    @Mock ClienteRepository clienteRepository;
    @Mock TipoUsuarioRepository tipoUsuarioRepository;
    @Mock NotificacaoMapper mapper;
    @InjectMocks NotificacaoService service;

    Notificacao notificacao;
    NotificacaoRequestDTO requestDTO;
    NotificacaoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        notificacao = mock(Notificacao.class);
        requestDTO  = mock(NotificacaoRequestDTO.class);
        responseDTO = mock(NotificacaoResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        when(requestDTO.getClienteCpf()).thenReturn(12345678900L);
        when(requestDTO.getTipoUsuarioId()).thenReturn(1);
        when(clienteRepository.findByCpf("12345678900")).thenReturn(Optional.of(mock(Cliente.class)));
        when(tipoUsuarioRepository.findById(1)).thenReturn(Optional.of(mock(TipoUsuario.class)));
        when(mapper.toEntity(requestDTO)).thenReturn(notificacao);
        when(notificacaoRepository.save(notificacao)).thenReturn(notificacao);
        when(mapper.toResponse(notificacao)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(notificacaoRepository.findById(1)).thenReturn(Optional.of(notificacao));
        when(mapper.toResponse(notificacao)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca EntityNotFoundException quando nao existe")
    void getById_naoEncontrado() {
        when(notificacaoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(notificacaoRepository.findAll()).thenReturn(List.of(notificacao));
        when(mapper.toResponse(notificacao)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("delete: deleta quando existe")
    void delete_sucesso() {
        when(notificacaoRepository.existsById(1)).thenReturn(true);
        service.delete(1);
        verify(notificacaoRepository).deleteById(1);
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(notificacaoRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class);
        verify(notificacaoRepository, never()).deleteById(any());
    }
}
