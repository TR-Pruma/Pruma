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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissaoUsuarioServiceTest {

    @Mock PermissaoUsuarioRepository repository;
    @Mock PermissaoUsuarioMapper mapper;
    @InjectMocks PermissaoUsuarioServiceImpl service;

    PermissaoUsuario entity;
    PermissaoUsuarioResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(PermissaoUsuario.class);
        responseDTO = mock(PermissaoUsuarioResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        var dto = mock(PermissaoUsuarioRequestDTO.class);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.create(dto)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.getById(1L)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca RecursoNaoEncontradoException quando nao existe")
    void getById_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("list: retorna pagina mapeada")
    void list_paginado() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.list(pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listByClienteCpf: retorna lista filtrada por CPF")
    void listByClienteCpf() {
        when(repository.findByCliente_Cpf("111")).thenReturn(List.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listByClienteCpf("111")).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza permissao quando fornecida")
    void update_comPermissao() {
        var updateDTO = mock(PermissaoUsuarioUpdateDTO.class);
        when(updateDTO.permissao()).thenReturn("ADMIN");
        when(updateDTO.tipoUsuarioId()).thenReturn(null);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.update(1L, updateDTO)).isEqualTo(responseDTO);
        verify(entity).setPermissao("ADMIN");
    }

    @Test
    @DisplayName("update: atualiza tipoUsuario quando id fornecido")
    void update_comTipoUsuario() {
        var updateDTO = mock(PermissaoUsuarioUpdateDTO.class);
        when(updateDTO.permissao()).thenReturn(null);
        when(updateDTO.tipoUsuarioId()).thenReturn(3);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.update(1L, updateDTO)).isEqualTo(responseDTO);
        verify(entity).setTipoUsuario(any());
    }

    @Test
    @DisplayName("update: lanca RecursoNaoEncontradoException quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(PermissaoUsuarioUpdateDTO.class);
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, updateDTO))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("delete: soft-delete quando existe")
    void delete_sucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);

        verify(entity).setAtivo(false);
        verify(repository).save(entity);
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("delete: lanca RecursoNaoEncontradoException quando nao existe")
    void delete_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99L))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}
