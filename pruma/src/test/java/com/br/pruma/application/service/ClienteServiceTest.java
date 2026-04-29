package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.application.dto.update.ClienteUpdateDTO;
import com.br.pruma.application.mapper.ClienteMapper;
import com.br.pruma.application.service.impl.ClienteServiceImpl;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Endereco;
import com.br.pruma.core.repository.ClienteRepository;
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
class ClienteServiceTest {

    @Mock ClienteRepository clienteRepository;
    @Mock EnderecoRepository enderecoRepository;
    @Mock ClienteMapper clienteMapper;
    @InjectMocks ClienteServiceImpl service;

    Cliente entity;
    ClienteResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(Cliente.class);
        responseDTO = mock(ClienteResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        var requestDTO = mock(ClienteRequestDTO.class);
        var endereco   = mock(Endereco.class);
        when(requestDTO.enderecoId()).thenReturn(1);
        when(enderecoRepository.findById(1)).thenReturn(Optional.of(endereco));
        when(clienteMapper.toEntity(requestDTO, endereco)).thenReturn(entity);
        when(clienteRepository.save(entity)).thenReturn(entity);
        when(clienteMapper.toDto(entity)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("create: lanca excecao quando endereco nao encontrado")
    void create_enderecoNaoEncontrado() {
        var requestDTO = mock(ClienteRequestDTO.class);
        when(requestDTO.enderecoId()).thenReturn(99);
        when(enderecoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(requestDTO))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("findById: retorna DTO quando existe")
    void findById_encontrado() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(entity));
        when(clienteMapper.toDto(entity)).thenReturn(responseDTO);

        assertThat(service.findById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("findById: lanca excecao quando nao existe")
    void findById_naoEncontrado() {
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("findAll: retorna lista mapeada")
    void findAll() {
        when(clienteRepository.findAll()).thenReturn(List.of(entity));
        when(clienteMapper.toDto(entity)).thenReturn(responseDTO);

        assertThat(service.findAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("findAll: retorna lista vazia quando nao ha clientes")
    void findAll_vazia() {
        when(clienteRepository.findAll()).thenReturn(List.of());

        assertThat(service.findAll()).isEmpty();
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(ClienteUpdateDTO.class);
        when(clienteRepository.findById(1)).thenReturn(Optional.of(entity));
        when(clienteRepository.save(entity)).thenReturn(entity);
        when(clienteMapper.toDto(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(clienteMapper).updateFromDto(updateDTO, entity);
    }

    @Test
    @DisplayName("update: lanca excecao quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(ClienteUpdateDTO.class);
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("delete: soft-delete quando existe")
    void delete_sucesso() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(entity));

        service.delete(1);

        verify(entity).setAtivo(false);
        verify(clienteRepository).save(entity);
        verify(clienteRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("delete: lanca excecao quando nao existe")
    void delete_naoEncontrado() {
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}
