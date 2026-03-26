package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ObraRequestDTO;
import com.br.pruma.application.dto.response.ObraResponseDTO;
import com.br.pruma.application.dto.update.ObraUpdateDTO;
import com.br.pruma.application.mapper.ObraMapper;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.repository.ObraRepository;
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
class ObraServiceTest {

    @Mock ObraRepository repository;
    @Mock ObraMapper mapper;
    @InjectMocks ObraService service;

    Obra obra;
    ObraRequestDTO requestDTO;
    ObraResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        obra        = mock(Obra.class);
        requestDTO  = mock(ObraRequestDTO.class);
        responseDTO = mock(ObraResponseDTO.class);
    }

    @Test
    @DisplayName("criar: salva e retorna DTO")
    void criar_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(obra);
        when(repository.save(obra)).thenReturn(obra);
        when(mapper.toResponse(obra)).thenReturn(responseDTO);

        assertThat(service.criar(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorId: retorna DTO quando existe")
    void buscarPorId_encontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(obra));
        when(mapper.toResponse(obra)).thenReturn(responseDTO);

        assertThat(service.buscarPorId(1L)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorId: lanca EntityNotFoundException quando nao existe")
    void buscarPorId_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("listarTodas: retorna lista mapeada")
    void listarTodas() {
        when(repository.findAll()).thenReturn(List.of(obra));
        when(mapper.toResponse(obra)).thenReturn(responseDTO);

        assertThat(service.listarTodas()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("atualizar: atualiza quando existe")
    void atualizar_sucesso() {
        var updateDTO = mock(ObraUpdateDTO.class);
        when(repository.findById(1L)).thenReturn(Optional.of(obra));
        when(repository.save(obra)).thenReturn(obra);
        when(mapper.toResponse(obra)).thenReturn(responseDTO);

        assertThat(service.atualizar(1L, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, obra);
    }

    @Test
    @DisplayName("atualizar: lanca EntityNotFoundException quando nao existe")
    void atualizar_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99L, mock(ObraUpdateDTO.class)))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("deletar: deleta quando existe")
    void deletar_sucesso() {
        when(repository.existsById(1L)).thenReturn(true);
        service.deletar(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("deletar: lanca EntityNotFoundException quando nao existe")
    void deletar_naoEncontrado() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.deletar(99L))
                .isInstanceOf(EntityNotFoundException.class);
        verify(repository, never()).deleteById(any());
    }
}
