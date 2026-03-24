package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.PermissaoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.PermissaoUsuarioResponseDTO;
import com.br.pruma.application.mapper.PermissaoUsuarioMapper;
import com.br.pruma.core.domain.PermissaoUsuario;
import com.br.pruma.core.repository.PermissaoUsuarioRepository;
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
class PermissaoUsuarioServiceTest {

    @Mock
    private PermissaoUsuarioRepository repository;

    @Mock
    private PermissaoUsuarioMapper mapper;

    @InjectMocks
    private PermissaoUsuarioService service;

    private PermissaoUsuario permissao;
    private PermissaoUsuarioRequestDTO requestDTO;
    private PermissaoUsuarioResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        permissao = new PermissaoUsuario();
        requestDTO = new PermissaoUsuarioRequestDTO();
        responseDTO = new PermissaoUsuarioResponseDTO();
    }

    // -----------------------------------------------------------------------
    // criar
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("criar: deve salvar e retornar DTO")
    void criar_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(permissao);
        when(repository.save(permissao)).thenReturn(permissao);
        when(mapper.toResponseDTO(permissao)).thenReturn(responseDTO);

        PermissaoUsuarioResponseDTO result = service.criar(requestDTO);

        assertThat(result).isNotNull();
        verify(repository).save(permissao);
    }

    // -----------------------------------------------------------------------
    // buscarPorId
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("buscarPorId: deve retornar DTO quando permissão existe")
    void buscarPorId_encontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(permissao));
        when(mapper.toResponseDTO(permissao)).thenReturn(responseDTO);

        PermissaoUsuarioResponseDTO result = service.buscarPorId(1L);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("buscarPorId: deve lançar EntityNotFoundException quando não encontrar")
    void buscarPorId_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    // -----------------------------------------------------------------------
    // atualizar
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("atualizar: deve atualizar permissão existente")
    void atualizar_sucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(permissao));
        when(mapper.toEntity(requestDTO)).thenReturn(permissao);
        when(repository.save(permissao)).thenReturn(permissao);
        when(mapper.toResponseDTO(permissao)).thenReturn(responseDTO);

        PermissaoUsuarioResponseDTO result = service.atualizar(1L, requestDTO);

        assertThat(result).isNotNull();
        verify(repository).save(permissao);
    }

    @Test
    @DisplayName("atualizar: deve lançar EntityNotFoundException quando permissão não existe")
    void atualizar_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99L, requestDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    // -----------------------------------------------------------------------
    // deletar
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("deletar: deve deletar permissão existente")
    void deletar_sucesso() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deletar(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("deletar: deve lançar EntityNotFoundException quando não existe")
    void deletar_naoEncontrado() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.deletar(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");

        verify(repository, never()).deleteById(any());
    }

    // -----------------------------------------------------------------------
    // buscarPorCliente
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("buscarPorCliente: deve retornar lista de permissões do CPF")
    void buscarPorCliente_retornaLista() {
        when(repository.findByCliente_Cpf("12345678900")).thenReturn(List.of(permissao));
        when(mapper.toResponseDTO(permissao)).thenReturn(responseDTO);

        List<PermissaoUsuarioResponseDTO> result = service.buscarPorCliente("12345678900");

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("buscarPorCliente: deve retornar lista vazia quando CPF não tem permissões")
    void buscarPorCliente_listaVazia() {
        when(repository.findByCliente_Cpf("00000000000")).thenReturn(List.of());

        List<PermissaoUsuarioResponseDTO> result = service.buscarPorCliente("00000000000");

        assertThat(result).isEmpty();
    }

    // -----------------------------------------------------------------------
    // buscarPorTipoUsuario
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("buscarPorTipoUsuario: deve retornar lista de permissões do tipo")
    void buscarPorTipoUsuario_retornaLista() {
        when(repository.findByTipoUsuario_Id(1)).thenReturn(List.of(permissao));
        when(mapper.toResponseDTO(permissao)).thenReturn(responseDTO);

        List<PermissaoUsuarioResponseDTO> result = service.buscarPorTipoUsuario(1);

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("buscarPorTipoUsuario: deve retornar lista vazia quando tipo não tem permissões")
    void buscarPorTipoUsuario_listaVazia() {
        when(repository.findByTipoUsuario_Id(99)).thenReturn(List.of());

        List<PermissaoUsuarioResponseDTO> result = service.buscarPorTipoUsuario(99);

        assertThat(result).isEmpty();
    }
}
