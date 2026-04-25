package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.TipoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.TipoUsuarioResponseDTO;
import com.br.pruma.application.dto.update.TipoUsuarioUpdateDTO;
import com.br.pruma.application.mapper.TipoUsuarioMapper;
import com.br.pruma.application.service.impl.TipoUsuarioServiceImpl;
import com.br.pruma.core.domain.TipoUsuario;
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
class TipoUsuarioServiceTest {

    @Mock TipoUsuarioRepository repository;
    @Mock TipoUsuarioMapper mapper;
    @InjectMocks TipoUsuarioServiceImpl service;

    TipoUsuario tipoUsuario;
    TipoUsuarioRequestDTO requestDTO;
    TipoUsuarioResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        tipoUsuario = mock(TipoUsuario.class);
        requestDTO  = mock(TipoUsuarioRequestDTO.class);
        responseDTO = mock(TipoUsuarioResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(tipoUsuario);
        when(repository.save(tipoUsuario)).thenReturn(tipoUsuario);
        when(mapper.toResponse(tipoUsuario)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(tipoUsuario));
        when(mapper.toResponse(tipoUsuario)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca EntityNotFoundException quando nao existe")
    void getById_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(repository.findAll()).thenReturn(List.of(tipoUsuario));
        when(mapper.toResponse(tipoUsuario)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(TipoUsuarioUpdateDTO.class);
        when(repository.findById(1)).thenReturn(Optional.of(tipoUsuario));
        when(repository.save(tipoUsuario)).thenReturn(tipoUsuario);
        when(mapper.toResponse(tipoUsuario)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, tipoUsuario);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, mock(TipoUsuarioUpdateDTO.class)))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("delete: deleta quando existe")
    void delete_sucesso() {
        when(repository.existsById(1)).thenReturn(true);
        service.delete(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(repository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class);
        verify(repository, never()).deleteById(any());
    }
    @Test
    @DisplayName("listAll: retorna lista vazia quando nao ha tipos")
    void listAll_vazia() {
        when(repository.findAll()).thenReturn(List.of());

        assertThat(service.listAll()).isEmpty();
    }
}
