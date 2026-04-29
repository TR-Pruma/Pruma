package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ItemOrcamentoRequestDTO;
import com.br.pruma.application.dto.response.ItemOrcamentoResponseDTO;
import com.br.pruma.application.dto.update.ItemOrcamentoUpdateDTO;
import com.br.pruma.application.mapper.ItemOrcamentoMapper;
import com.br.pruma.application.service.impl.ItemOrcamentoServiceImpl;
import com.br.pruma.core.domain.ItemOrcamento;
import com.br.pruma.core.repository.port.ItemOrcamentoRepositoryPort;
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
class ItemOrcamentoServiceTest {

    @Mock ItemOrcamentoRepositoryPort itemOrcamentoRepositoryPort;
    @Mock ItemOrcamentoMapper itemOrcamentoMapper;
    @InjectMocks ItemOrcamentoServiceImpl service;

    ItemOrcamento entity;
    ItemOrcamentoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(ItemOrcamento.class);
        responseDTO = mock(ItemOrcamentoResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        var dto = mock(ItemOrcamentoRequestDTO.class);
        when(itemOrcamentoMapper.toEntity(dto)).thenReturn(entity);
        when(itemOrcamentoRepositoryPort.save(entity)).thenReturn(entity);
        when(itemOrcamentoMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.create(dto)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(itemOrcamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(itemOrcamentoMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca EntityNotFoundException quando nao existe")
    void getById_naoEncontrado() {
        when(itemOrcamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(itemOrcamentoRepositoryPort.findAll()).thenReturn(List.of(entity));
        when(itemOrcamentoMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("list: retorna pagina mapeada")
    void list_paginado() {
        Pageable pageable = PageRequest.of(0, 10);
        when(itemOrcamentoRepositoryPort.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));
        when(itemOrcamentoMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.list(pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listByOrcamento: retorna lista filtrada por orcamento")
    void listByOrcamento() {
        when(itemOrcamentoRepositoryPort.findByOrcamentoId(5)).thenReturn(List.of(entity));
        when(itemOrcamentoMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listByOrcamento(5)).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(ItemOrcamentoUpdateDTO.class);
        when(itemOrcamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(itemOrcamentoRepositoryPort.save(entity)).thenReturn(entity);
        when(itemOrcamentoMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(itemOrcamentoMapper).updateFromDto(updateDTO, entity);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(ItemOrcamentoUpdateDTO.class);
        when(itemOrcamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("replace: substitui quando existe")
    void replace_sucesso() {
        var dto = mock(ItemOrcamentoRequestDTO.class);
        when(itemOrcamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(itemOrcamentoMapper.toEntity(dto)).thenReturn(entity);
        when(itemOrcamentoRepositoryPort.save(entity)).thenReturn(entity);
        when(itemOrcamentoMapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.replace(1, dto)).isEqualTo(responseDTO);
        verify(entity).setId(1);
    }

    @Test
    @DisplayName("replace: lanca EntityNotFoundException quando nao existe")
    void replace_naoEncontrado() {
        var dto = mock(ItemOrcamentoRequestDTO.class);
        when(itemOrcamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("delete: deleta fisicamente quando existe")
    void delete_sucesso() {
        when(itemOrcamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));

        service.delete(1);

        verify(itemOrcamentoRepositoryPort).delete(entity);
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(itemOrcamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
