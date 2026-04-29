package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.MaterialUtilizadoRequestDTO;
import com.br.pruma.application.dto.response.MaterialUtilizadoResponseDTO;
import com.br.pruma.application.dto.update.MaterialUtilizadoUpdateDTO;
import com.br.pruma.application.mapper.MaterialUtilizadoMapper;
import com.br.pruma.application.service.impl.MaterialUtilizadoServiceImpl;
import com.br.pruma.core.domain.MaterialUtilizado;
import com.br.pruma.core.exception.RecursoNaoEncontradoException;
import com.br.pruma.core.repository.port.MaterialUtilizadoRepositoryPort;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterialUtilizadoServiceTest {

    @Mock MaterialUtilizadoRepositoryPort repositoryPort;
    @Mock MaterialUtilizadoMapper mapper;
    @InjectMocks MaterialUtilizadoServiceImpl service;

    MaterialUtilizado entity;
    MaterialUtilizadoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(MaterialUtilizado.class);
        responseDTO = mock(MaterialUtilizadoResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        var dto = mock(MaterialUtilizadoRequestDTO.class);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repositoryPort.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.create(dto)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(repositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca RecursoNaoEncontradoException quando nao existe")
    void getById_naoEncontrado() {
        when(repositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(repositoryPort.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("list: retorna pagina mapeada")
    void list_paginado() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repositoryPort.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.list(pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listByProjeto: retorna lista filtrada por projeto")
    void listByProjeto() {
        when(repositoryPort.findByProjetoId(3)).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listByProjeto(3)).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(MaterialUtilizadoUpdateDTO.class);
        when(repositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(repositoryPort.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateEntity(updateDTO, entity);
    }

    @Test
    @DisplayName("update: lanca RecursoNaoEncontradoException quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(MaterialUtilizadoUpdateDTO.class);
        when(repositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("replace: substitui quando existe")
    void replace_sucesso() {
        var dto = mock(MaterialUtilizadoRequestDTO.class);
        when(repositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repositoryPort.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.replace(1, dto)).isEqualTo(responseDTO);
        verify(entity).setId(1);
    }

    @Test
    @DisplayName("replace: lanca RecursoNaoEncontradoException quando nao existe")
    void replace_naoEncontrado() {
        var dto = mock(MaterialUtilizadoRequestDTO.class);
        when(repositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, dto))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("delete: deleta fisicamente quando existe")
    void delete_sucesso() {
        when(repositoryPort.findById(1)).thenReturn(Optional.of(entity));

        service.delete(1);

        verify(repositoryPort).deleteById(1);
    }

    @Test
    @DisplayName("delete: lanca RecursoNaoEncontradoException quando nao existe")
    void delete_naoEncontrado() {
        when(repositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}
