package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.FornecedorRequestDTO;
import com.br.pruma.application.dto.response.FornecedorResponseDTO;
import com.br.pruma.application.dto.update.FornecedorUpdateDTO;
import com.br.pruma.application.mapper.FornecedorMapper;
import com.br.pruma.core.domain.Fornecedor;
import com.br.pruma.core.repository.FornecedorRepository;
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
class FornecedorServiceTest {

    @Mock FornecedorRepository repository;
    @Mock FornecedorMapper mapper;
    @InjectMocks FornecedorService service;

    Fornecedor fornecedor;
    FornecedorRequestDTO requestDTO;
    FornecedorResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        fornecedor  = mock(Fornecedor.class);
        requestDTO  = mock(FornecedorRequestDTO.class);
        responseDTO = mock(FornecedorResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(fornecedor);
        when(repository.save(fornecedor)).thenReturn(fornecedor);
        when(mapper.toResponse(fornecedor)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(mapper.toResponse(fornecedor)).thenReturn(responseDTO);

        assertThat(service.getById(1L)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca EntityNotFoundException quando nao existe")
    void getById_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(repository.findAll()).thenReturn(List.of(fornecedor));
        when(mapper.toResponse(fornecedor)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(FornecedorUpdateDTO.class);
        when(repository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(repository.save(fornecedor)).thenReturn(fornecedor);
        when(mapper.toResponse(fornecedor)).thenReturn(responseDTO);

        assertThat(service.update(1L, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, fornecedor);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, mock(FornecedorUpdateDTO.class)))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("delete: deleta quando existe")
    void delete_sucesso() {
        when(repository.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99L))
                .isInstanceOf(EntityNotFoundException.class);
        verify(repository, never()).deleteById(any());
    }
}
