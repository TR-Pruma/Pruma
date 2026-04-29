package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.PosObraRequestDTO;
import com.br.pruma.application.dto.response.PosObraResponseDTO;
import com.br.pruma.application.dto.update.PosObraUpdateDTO;
import com.br.pruma.application.mapper.PosObraMapper;
import com.br.pruma.application.service.impl.PosObraServiceImpl;
import com.br.pruma.core.domain.PosObra;
import com.br.pruma.core.repository.port.PosObraRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
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
class PosObraServiceTest {

    @Mock PosObraRepositoryPort posObraRepositoryPort;
    @Mock PosObraMapper posObraMapper;
    @InjectMocks PosObraServiceImpl service;

    PosObra entity;
    PosObraResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(PosObra.class);
        responseDTO = mock(PosObraResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        var dto = mock(PosObraRequestDTO.class);
        when(posObraMapper.toEntity(dto)).thenReturn(entity);
        when(posObraRepositoryPort.save(entity)).thenReturn(entity);
        when(posObraMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.create(dto)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe (id convertido para Long)")
    void getById_encontrado() {
        when(posObraRepositoryPort.findById(1L)).thenReturn(Optional.of(entity));
        when(posObraMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca EntityNotFoundException quando nao existe")
    void getById_naoEncontrado() {
        when(posObraRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(posObraRepositoryPort.findAll()).thenReturn(List.of(entity));
        when(posObraMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("list: retorna pagina mapeada")
    void list_paginado() {
        Pageable pageable = PageRequest.of(0, 10);
        when(posObraRepositoryPort.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));
        when(posObraMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.list(pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listByProjeto: retorna lista filtrada por obra (id convertido para Long)")
    void listByProjeto() {
        when(posObraRepositoryPort.findAllByObraId(2L)).thenReturn(List.of(entity));
        when(posObraMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listByProjeto(2)).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(PosObraUpdateDTO.class);
        when(posObraRepositoryPort.findById(1L)).thenReturn(Optional.of(entity));
        when(posObraRepositoryPort.save(entity)).thenReturn(entity);
        when(posObraMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(posObraMapper).updateFromDto(updateDTO, entity);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(PosObraUpdateDTO.class);
        when(posObraRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("replace: substitui quando existe")
    void replace_sucesso() {
        var dto = mock(PosObraRequestDTO.class);
        when(posObraRepositoryPort.findById(1L)).thenReturn(Optional.of(entity));
        when(posObraMapper.toEntity(dto)).thenReturn(entity);
        when(posObraRepositoryPort.save(entity)).thenReturn(entity);
        when(posObraMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.replace(1, dto)).isEqualTo(responseDTO);
        verify(entity).setId(1L);
    }

    @Test
    @DisplayName("replace: lanca EntityNotFoundException quando nao existe")
    void replace_naoEncontrado() {
        var dto = mock(PosObraRequestDTO.class);
        when(posObraRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("delete: deleta fisicamente quando existe")
    void delete_sucesso() {
        when(posObraRepositoryPort.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1);

        verify(posObraRepositoryPort).delete(entity);
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(posObraRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
