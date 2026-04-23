package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.PermissaoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.PermissaoUsuarioResponseDTO;
import com.br.pruma.application.dto.update.PermissaoUsuarioUpdateDTO;
import com.br.pruma.application.mapper.PermissaoUsuarioMapper;
import com.br.pruma.application.service.impl.PermissaoUsuarioServiceImpl;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.PermissaoUsuario;
import com.br.pruma.core.repository.PermissaoUsuarioRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissaoUsuarioServiceTest {

    @Mock
    private PermissaoUsuarioRepository repository;

    @Mock
    private PermissaoUsuarioMapper mapper;

    @InjectMocks
    private PermissaoUsuarioServiceImpl service;

    private PermissaoUsuario permissao;
    private PermissaoUsuarioRequestDTO requestDTO;
    private PermissaoUsuarioUpdateDTO updateDTO;
    private PermissaoUsuarioResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        permissao   = mock(PermissaoUsuario.class);
        requestDTO  = mock(PermissaoUsuarioRequestDTO.class);
        updateDTO   = mock(PermissaoUsuarioUpdateDTO.class);
        responseDTO = mock(PermissaoUsuarioResponseDTO.class);
    }

    // -----------------------------------------------------------------------
    // create
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("create: deve salvar e retornar DTO")
    void create_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(permissao);
        when(repository.save(permissao)).thenReturn(permissao);
        when(mapper.toResponseDTO(permissao)).thenReturn(responseDTO);

        PermissaoUsuarioResponseDTO result = service.create(requestDTO);

        assertThat(result).isNotNull();
        verify(repository).save(permissao);
    }

    // -----------------------------------------------------------------------
    // getById
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("getById: deve retornar DTO quando permissao existe")
    void getById_encontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(permissao));
        when(mapper.toResponseDTO(permissao)).thenReturn(responseDTO);

        PermissaoUsuarioResponseDTO result = service.getById(1L);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("getById: deve lancar RecursoNaoEncontradoException quando nao encontrar")
    void getById_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }

    // -----------------------------------------------------------------------
    // listAll
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("listAll: deve retornar lista mapeada")
    void listAll() {
        when(repository.findAll()).thenReturn(List.of(permissao));
        when(mapper.toResponseDTO(permissao)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    // -----------------------------------------------------------------------
    // update
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("update: deve atualizar permissao existente")
    void update_sucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(permissao));
        when(repository.save(permissao)).thenReturn(permissao);
        when(mapper.toResponseDTO(permissao)).thenReturn(responseDTO);

        PermissaoUsuarioResponseDTO result = service.update(1L, updateDTO);

        assertThat(result).isNotNull();
        verify(repository).save(permissao);
    }

    @Test
    @DisplayName("update: deve lancar RecursoNaoEncontradoException quando nao existe")
    void update_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, updateDTO))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }

    // -----------------------------------------------------------------------
    // delete
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("delete: deve deletar permissao existente")
    void delete_sucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(permissao));

        service.delete(1L);

        repository.save(permissao);
    }

    @Test
    @DisplayName("delete: deve lancar RecursoNaoEncontradoException quando nao existe")
    void delete_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99L))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
        verify(repository, never()).delete(any(PermissaoUsuario.class));
    }
}
