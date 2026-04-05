package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ProjetoRequestDTO;
import com.br.pruma.application.dto.response.ProjetoResponseDTO;
import com.br.pruma.application.dto.update.ProjetoUpdateDTO;
import com.br.pruma.application.mapper.ProjetoMapper;
import com.br.pruma.application.service.impl.ProjetoServiceImpl;
import com.br.pruma.core.domain.Projeto;
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
class ProjetoServiceTest {

    @Mock ProjetoRepository repository;
    @Mock ProjetoMapper mapper;
    @InjectMocks ProjetoServiceImpl service;

    Projeto projeto;
    ProjetoRequestDTO requestDTO;
    ProjetoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        projeto     = mock(Projeto.class);
        requestDTO  = mock(ProjetoRequestDTO.class);
        responseDTO = mock(ProjetoResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(projeto);
        when(repository.save(projeto)).thenReturn(projeto);
        when(mapper.toResponse(projeto)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(projeto));
        when(mapper.toResponse(projeto)).thenReturn(responseDTO);

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
        when(repository.findAll()).thenReturn(List.of(projeto));
        when(mapper.toResponse(projeto)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(ProjetoUpdateDTO.class);
        when(repository.findById(1)).thenReturn(Optional.of(projeto));
        when(repository.save(projeto)).thenReturn(projeto);
        when(mapper.toResponse(projeto)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, projeto);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, mock(ProjetoUpdateDTO.class)))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("delete: deleta quando existe")
    void delete_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(projeto));
        service.delete(1);
        verify(repository).delete(projeto);
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class);
        verify(repository, never()).delete(any(Projeto.class));
    }
}
