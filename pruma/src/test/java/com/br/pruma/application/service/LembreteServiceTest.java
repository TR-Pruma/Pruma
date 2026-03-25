package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.LembreteRequestDTO;
import com.br.pruma.application.dto.response.LembreteResponseDTO;
import com.br.pruma.application.dto.update.LembreteUpdateDTO;
import com.br.pruma.application.mapper.LembreteMapper;
import com.br.pruma.core.domain.Lembrete;
import com.br.pruma.core.repository.LembreteRepository;
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
class LembreteServiceTest {

    @Mock LembreteRepository repository;
    @Mock LembreteMapper mapper;
    @InjectMocks LembreteService service;

    Lembrete lembrete;
    LembreteRequestDTO requestDTO;
    LembreteResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        lembrete    = mock(Lembrete.class);
        requestDTO  = mock(LembreteRequestDTO.class);
        responseDTO = mock(LembreteResponseDTO.class);
    }

    @Test
    @DisplayName("criar: salva e retorna DTO")
    void criar_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(lembrete);
        when(repository.save(lembrete)).thenReturn(lembrete);
        when(mapper.toResponse(lembrete)).thenReturn(responseDTO);

        assertThat(service.criar(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorId: retorna DTO quando existe")
    void buscarPorId_encontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(lembrete));
        when(mapper.toResponse(lembrete)).thenReturn(responseDTO);

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
    @DisplayName("listarTodos: retorna lista mapeada")
    void listarTodos() {
        when(repository.findAll()).thenReturn(List.of(lembrete));
        when(mapper.toResponse(lembrete)).thenReturn(responseDTO);

        assertThat(service.listarTodos()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("atualizar: atualiza quando existe")
    void atualizar_sucesso() {
        var updateDTO = mock(LembreteUpdateDTO.class);
        when(repository.findById(1L)).thenReturn(Optional.of(lembrete));
        when(repository.save(lembrete)).thenReturn(lembrete);
        when(mapper.toResponse(lembrete)).thenReturn(responseDTO);

        assertThat(service.atualizar(1L, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, lembrete);
    }

    @Test
    @DisplayName("atualizar: lanca EntityNotFoundException quando nao existe")
    void atualizar_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99L, mock(LembreteUpdateDTO.class)))
                .isInstanceOf(EntityNotFoundException.class);
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
