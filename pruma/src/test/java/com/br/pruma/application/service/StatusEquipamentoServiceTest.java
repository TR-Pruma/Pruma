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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatusEquipamentoServiceTest {

    @Mock StatusEquipamentoRepositoryPort statusEquipamentoRepositoryPort;
    @Mock StatusEquipamentoMapper mapper;
    @InjectMocks StatusEquipamentoServiceImpl service;

    StatusEquipamento entity;
    StatusEquipamentoRequestDTO requestDTO;
    StatusEquipamentoUpdateDTO updateDTO;
    StatusEquipamentoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(StatusEquipamento.class);
        requestDTO  = mock(StatusEquipamentoRequestDTO.class);
        updateDTO   = mock(StatusEquipamentoUpdateDTO.class);
        responseDTO = mock(StatusEquipamentoResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(entity);
        when(statusEquipamentoRepositoryPort.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
        verify(statusEquipamentoRepositoryPort).save(entity);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(statusEquipamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca EntityNotFoundException quando nao existe")
    void getById_naoEncontrado() {
        when(statusEquipamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(statusEquipamentoRepositoryPort.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        when(statusEquipamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(statusEquipamentoRepositoryPort.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, entity);
        verify(statusEquipamentoRepositoryPort).save(entity);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        when(statusEquipamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("delete: soft delete quando existe")
    void delete_sucesso() {
        when(statusEquipamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));

        service.delete(1);

        verify(entity).setAtivo(false);
        verify(statusEquipamentoRepositoryPort).save(entity);
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(statusEquipamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
        verify(statusEquipamentoRepositoryPort, never()).save(any());
    }
    @Test
    @DisplayName("listAll: retorna lista vazia quando nao ha status")
    void listAll_vazia() {
        when(statusEquipamentoRepositoryPort.findAll()).thenReturn(List.of());

        assertThat(service.listAll()).isEmpty();
    }
}