package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ClienteTipoRequestDTO;
import com.br.pruma.application.dto.response.ClienteTipoResponseDTO;
import com.br.pruma.application.mapper.ClienteTipoMapper;
import com.br.pruma.application.service.impl.ClienteTipoServiceImpl;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.ClienteTipo;
import com.br.pruma.core.domain.TipoUsuario;
import com.br.pruma.core.repository.ClienteTipoRepository;
import com.br.pruma.core.repository.TipoUsuarioRepository;
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
class ClienteTipoServiceTest {

    @Mock ClienteTipoRepository repository;
    @Mock TipoUsuarioRepository tipoUsuarioRepository;
    @Mock ClienteTipoMapper mapper;
    @InjectMocks ClienteTipoServiceImpl service;

    ClienteTipo entity;
    ClienteTipoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(ClienteTipo.class);
        responseDTO = mock(ClienteTipoResponseDTO.class);
    }

    @Test
    @DisplayName("criar: salva e retorna DTO")
    void criar_sucesso() {
        var request     = mock(ClienteTipoRequestDTO.class);
        var tipoUsuario = mock(TipoUsuario.class);
        when(request.getTipoUsuarioId()).thenReturn(1);
        when(tipoUsuarioRepository.findById(1)).thenReturn(Optional.of(tipoUsuario));
        when(mapper.toEntity(request, tipoUsuario)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.criar(request)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("criar: lanca excecao quando TipoUsuario nao encontrado")
    void criar_tipoUsuarioNaoEncontrado() {
        var request = mock(ClienteTipoRequestDTO.class);
        when(request.getTipoUsuarioId()).thenReturn(99);
        when(tipoUsuarioRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.criar(request))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("buscarPorId: retorna DTO quando existe")
    void buscarPorId_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.buscarPorId(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorId: lanca excecao quando nao existe")
    void buscarPorId_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("listarTodos: retorna lista mapeada")
    void listarTodos() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listarTodos()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listarTodos: retorna lista vazia quando nao ha registros")
    void listarTodos_vazia() {
        when(repository.findAll()).thenReturn(List.of());

        assertThat(service.listarTodos()).isEmpty();
    }

    @Test
    @DisplayName("atualizar: atualiza quando existe")
    void atualizar_sucesso() {
        var request     = mock(ClienteTipoRequestDTO.class);
        var tipoUsuario = mock(TipoUsuario.class);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(request.getTipoUsuarioId()).thenReturn(2);
        when(tipoUsuarioRepository.findById(2)).thenReturn(Optional.of(tipoUsuario));
        when(mapper.toEntity(request, tipoUsuario)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.atualizar(1, request)).isEqualTo(responseDTO);
        verify(entity).setId(1);
    }

    @Test
    @DisplayName("atualizar: lanca excecao quando nao existe")
    void atualizar_naoEncontrado() {
        var request = mock(ClienteTipoRequestDTO.class);
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99, request))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("deletar: soft-delete quando existe")
    void deletar_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        service.deletar(1);

        verify(entity).setAtivo(false);
        verify(repository).save(entity);
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("deletar: lanca excecao quando nao existe")
    void deletar_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}
