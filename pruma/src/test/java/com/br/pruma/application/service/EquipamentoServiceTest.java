package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoResponseDTO;
import com.br.pruma.application.mapper.EquipamentoMapper;
import com.br.pruma.core.domain.Equipamento;
import com.br.pruma.core.repository.EquipamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipamentoServiceTest {

    @Mock EquipamentoRepository repository;
    @Mock EquipamentoMapper mapper;
    @InjectMocks EquipamentoService service;

    Equipamento equipamento;
    EquipamentoRequestDTO requestDTO;
    EquipamentoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        equipamento = mock(Equipamento.class);
        requestDTO  = mock(EquipamentoRequestDTO.class);
        responseDTO = mock(EquipamentoResponseDTO.class);
    }

    @Test
    @DisplayName("criar: salva e retorna DTO")
    void criar_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(equipamento);
        when(repository.save(equipamento)).thenReturn(equipamento);
        when(mapper.toResponseDto(equipamento)).thenReturn(responseDTO);

        assertThat(service.criar(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorId: retorna DTO quando existe")
    void buscarPorId_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(equipamento));
        when(mapper.toResponseDto(equipamento)).thenReturn(responseDTO);

        assertThat(service.buscarPorId(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorId: lanca EntityNotFoundException quando nao existe")
    void buscarPorId_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("atualizar: atualiza quando existe")
    void atualizar_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(equipamento));
        when(repository.save(equipamento)).thenReturn(equipamento);
        when(mapper.toResponseDto(equipamento)).thenReturn(responseDTO);

        assertThat(service.atualizar(1, requestDTO)).isEqualTo(responseDTO);
        verify(mapper).updateEntityFromDto(requestDTO, equipamento);
    }

    @Test
    @DisplayName("atualizar: lanca EntityNotFoundException quando nao existe")
    void atualizar_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99, requestDTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("deletar: deleta quando existe")
    void deletar_sucesso() {
        service.deletar(1);
        verify(repository).deleteById(1);
    }
}
