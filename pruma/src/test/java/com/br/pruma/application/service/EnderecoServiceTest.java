package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.EnderecoRequestDTO;
import com.br.pruma.application.dto.response.EnderecoResponseDTO;
import com.br.pruma.application.mapper.EnderecoMapper;
import com.br.pruma.application.service.impl.EnderecoServiceImpl;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Endereco;
import com.br.pruma.core.repository.EnderecoRepository;
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
class EnderecoServiceTest {

    @Mock EnderecoRepository repository;
    @Mock EnderecoMapper mapper;
    @InjectMocks EnderecoServiceImpl service;

    Endereco entity;
    EnderecoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(Endereco.class);
        responseDTO = mock(EnderecoResponseDTO.class);
    }

    @Test
    @DisplayName("salvar: persiste e retorna DTO")
    void salvar_sucesso() {
        var dto = mock(EnderecoRequestDTO.class);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.salvar(dto)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorId: retorna DTO quando existe")
    void buscarPorId_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

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
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

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
        var dto = mock(EnderecoRequestDTO.class);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.atualizar(1, dto)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(dto, entity);
    }

    @Test
    @DisplayName("atualizar: lanca excecao quando nao existe")
    void atualizar_naoEncontrado() {
        var dto = mock(EnderecoRequestDTO.class);
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99, dto))
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
