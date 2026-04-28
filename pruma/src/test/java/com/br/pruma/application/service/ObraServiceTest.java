package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ObraRequestDTO;
import com.br.pruma.application.dto.response.ObraResponseDTO;
import com.br.pruma.application.dto.update.ObraUpdateDTO;
import com.br.pruma.application.mapper.ObraMapper;
import com.br.pruma.application.service.impl.ObraServiceImpl;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ObraRepository;
import com.br.pruma.core.repository.ProjetoRepository;
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

    @Mock ObraRepository obraRepository;
    @Mock ProjetoRepository projetoRepository;
    @Mock ObraMapper mapper;
    @InjectMocks ObraServiceImpl service;

    // mock() bypass o @NoArgsConstructor(access = PROTECTED)
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
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        when(requestDTO.getProjetoId()).thenReturn(1);
        when(projetoRepository.findById(1)).thenReturn(Optional.of(mock(Projeto.class)));
        when(mapper.toEntity(requestDTO)).thenReturn(obra);
        when(obraRepository.save(obra)).thenReturn(obra);
        when(mapper.toResponse(obra)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(obraRepository.findById(1)).thenReturn(Optional.of(obra));
        when(mapper.toResponse(obra)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca EntityNotFoundException quando nao existe")
    void getById_naoEncontrado() {
        when(obraRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(obraRepository.findAll()).thenReturn(List.of(obra));
        when(mapper.toResponse(obra)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(ObraUpdateDTO.class);
        when(updateDTO.getProjetoId()).thenReturn(null);
        when(obraRepository.findById(1)).thenReturn(Optional.of(obra));
        when(obraRepository.save(obra)).thenReturn(obra);
        when(mapper.toResponse(obra)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, obra);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(ObraUpdateDTO.class);
        when(obraRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    // SUBSTITUIR o delete_sucesso existente por:
    @Test
    @DisplayName("delete: soft-delete quando existe")
    void delete_sucesso() {
        when(obraRepository.findById(1)).thenReturn(Optional.of(obra));

        service.delete(1);

        verify(obra).setAtivo(false);
        verify(obraRepository).save(obra);
        verify(obraRepository, never()).deleteById(any());
    }

    // ADICIONAR:
    @Test
    @DisplayName("listAll: retorna lista vazia quando nao ha obras")
    void listAll_vazia() {
        when(obraRepository.findAll()).thenReturn(List.of());

        assertThat(service.listAll()).isEmpty();
    }

    @Test
    @DisplayName("update: atualiza projeto quando projetoId nao e nulo no updateDTO")
    void update_comTrocaDeProjeto() {
        var updateDTO = mock(ObraUpdateDTO.class);
        when(updateDTO.getProjetoId()).thenReturn(2);
        when(obraRepository.findById(1)).thenReturn(Optional.of(obra));
        when(projetoRepository.findById(2)).thenReturn(Optional.of(mock(Projeto.class)));
        when(obraRepository.save(obra)).thenReturn(obra);
        when(mapper.toResponse(obra)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(obra).setProjeto(any(Projeto.class));
    }
}
