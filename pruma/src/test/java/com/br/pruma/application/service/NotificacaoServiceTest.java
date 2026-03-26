package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.NotificacaoRequestDTO;
import com.br.pruma.application.dto.response.NotificacaoResponseDTO;
import com.br.pruma.application.mapper.NotificacaoMapper;
import com.br.pruma.core.domain.Notificacao;
import com.br.pruma.core.repository.NotificacaoRepository;
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

    @Mock NotificacaoRepository repository;
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
    @DisplayName("criar: salva e retorna DTO")
    void criar_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(notificacao);
        when(repository.save(notificacao)).thenReturn(notificacao);
        when(mapper.toResponse(notificacao)).thenReturn(responseDTO);

        assertThat(service.criar(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorId: retorna DTO quando existe")
    void buscarPorId_encontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(notificacao));
        when(mapper.toResponse(notificacao)).thenReturn(responseDTO);

        assertThat(service.buscarPorId(1L)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorId: lanca EntityNotFoundException quando nao existe")
    void buscarPorId_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("listarPorCliente: retorna lista pelo CPF")
    void listarPorCliente() {
        when(repository.findAllByCliente_Cpf("12345678900")).thenReturn(List.of(notificacao));
        when(mapper.toResponse(notificacao)).thenReturn(responseDTO);

        assertThat(service.listarPorCliente("12345678900")).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("deletar: deleta quando existe")
    void deletar_sucesso() {
        when(repository.existsById(1L)).thenReturn(true);
        service.deletar(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("deletar: lanca EntityNotFoundException quando nao existe")
    void deletar_naoEncontrado() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.deletar(99L))
                .isInstanceOf(EntityNotFoundException.class);
        verify(repository, never()).deleteById(any());
    }
}
