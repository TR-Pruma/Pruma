package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.StatusEquipamentoRequestDTO;
import com.br.pruma.application.dto.response.StatusEquipamentoResponseDTO;
import com.br.pruma.application.dto.update.StatusEquipamentoUpdateDTO;
import com.br.pruma.application.mapper.StatusEquipamentoMapper;
import com.br.pruma.application.service.impl.StatusEquipamentoServiceImpl;
import com.br.pruma.core.domain.StatusEquipamento;
import com.br.pruma.core.repository.port.StatusEquipamentoRepositoryPort;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatusEquipamentoServiceTest {

    @Mock StatusEquipamentoRepositoryPort statusEquipamentoRepositoryPort;
    @Mock StatusEquipamentoMapper statusEquipamentoMapper;
    @InjectMocks StatusEquipamentoServiceImpl service;

    StatusEquipamento entity;
    StatusEquipamentoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(StatusEquipamento.class);
        responseDTO = mock(StatusEquipamentoResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        var dto = mock(StatusEquipamentoRequestDTO.class);
        when(statusEquipamentoMapper.toEntity(dto)).thenReturn(entity);
        when(statusEquipamentoRepositoryPort.save(entity)).thenReturn(entity);
        when(statusEquipamentoMapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.create(dto)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(statusEquipamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(statusEquipamentoMapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca EntityNotFoundException quando nao existe")
    void getById_naoEncontrado() {
        when(statusEquipamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(statusEquipamentoRepositoryPort.findAll()).thenReturn(List.of(entity));
        when(statusEquipamentoMapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("list: retorna pagina mapeada")
    void list_paginado() {
        Pageable pageable = PageRequest.of(0, 10);
        when(statusEquipamentoRepositoryPort.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));
        when(statusEquipamentoMapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.list(pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(StatusEquipamentoUpdateDTO.class);
        when(statusEquipamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(statusEquipamentoRepositoryPort.save(entity)).thenReturn(entity);
        when(statusEquipamentoMapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(statusEquipamentoMapper).updateFromDto(updateDTO, entity);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(StatusEquipamentoUpdateDTO.class);
        when(statusEquipamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("replace: substitui quando existe")
    void replace_sucesso() {
        var dto = mock(StatusEquipamentoRequestDTO.class);
        when(statusEquipamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(statusEquipamentoMapper.toEntity(dto)).thenReturn(entity);
        when(statusEquipamentoRepositoryPort.save(entity)).thenReturn(entity);
        when(statusEquipamentoMapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.replace(1, dto)).isEqualTo(responseDTO);
        verify(entity).setId(1);
    }

    @Test
    @DisplayName("replace: lanca EntityNotFoundException quando nao existe")
    void replace_naoEncontrado() {
        var dto = mock(StatusEquipamentoRequestDTO.class);
        when(statusEquipamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("delete: soft-delete quando existe")
    void delete_sucesso() {
        when(statusEquipamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));

        service.delete(1);

        verify(entity).setAtivo(false);
        verify(statusEquipamentoRepositoryPort).save(entity);
        verify(statusEquipamentoRepositoryPort, never()).delete(any());
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(statusEquipamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
