package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoResponseDTO;
import com.br.pruma.application.dto.update.EquipamentoUpdateDTO;
import com.br.pruma.application.mapper.EquipamentoMapper;
import com.br.pruma.application.service.impl.EquipamentoServiceImpl;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Equipamento;
import com.br.pruma.core.repository.EquipamentoRepository;
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
    @InjectMocks EquipamentoServiceImpl service;

    Equipamento equipamento;
    EquipamentoRequestDTO requestDTO;
    EquipamentoUpdateDTO updateDTO;
    EquipamentoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        equipamento = mock(Equipamento.class);
        requestDTO  = mock(EquipamentoRequestDTO.class);
        updateDTO   = mock(EquipamentoUpdateDTO.class);
        responseDTO = mock(EquipamentoResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(equipamento);
        when(repository.save(equipamento)).thenReturn(equipamento);
        when(mapper.toResponseDto(equipamento)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(equipamento));
        when(mapper.toResponseDto(equipamento)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca RecursoNaoEncontradoException quando nao existe")
    void getById_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(equipamento));
        when(repository.save(equipamento)).thenReturn(equipamento);
        when(mapper.toResponseDto(equipamento)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateEntityFromDto(updateDTO, equipamento);
    }

    @Test
    @DisplayName("update: lanca RecursoNaoEncontradoException quando nao existe")
    void update_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("delete: deleta quando existe")
    void delete_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(equipamento));

        service.delete(1);

        verify(repository).save(equipamento);
    }
}
