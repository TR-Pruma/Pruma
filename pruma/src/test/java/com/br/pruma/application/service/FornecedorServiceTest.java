package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.FornecedorRequestDTO;
import com.br.pruma.application.dto.response.FornecedorResponseDTO;
import com.br.pruma.application.dto.update.FornecedorUpdateDTO;
import com.br.pruma.application.mapper.FornecedorMapper;
import com.br.pruma.application.service.impl.FornecedorServiceImpl;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Fornecedor;
import com.br.pruma.core.repository.FornecedorRepository;
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
class FornecedorServiceTest {

    @Mock FornecedorRepository repository;
    @Mock FornecedorMapper mapper;
    @InjectMocks FornecedorServiceImpl service;

    Fornecedor fornecedor;
    FornecedorRequestDTO requestDTO;
    FornecedorUpdateDTO updateDTO;
    FornecedorResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        fornecedor  = mock(Fornecedor.class);
        requestDTO  = mock(FornecedorRequestDTO.class);
        updateDTO   = mock(FornecedorUpdateDTO.class);
        responseDTO = mock(FornecedorResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(fornecedor);
        when(repository.save(fornecedor)).thenReturn(fornecedor);
        when(mapper.toResponse(fornecedor)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
        verify(repository).save(fornecedor);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(fornecedor));
        when(mapper.toResponse(fornecedor)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca RecursoNaoEncontradoException quando nao existe")
    void getById_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(RecursoNaoEncontradoException.class)
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
        when(repository.findById(1)).thenReturn(Optional.of(fornecedor));
        when(repository.save(fornecedor)).thenReturn(fornecedor);
        when(mapper.toResponse(fornecedor)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, fornecedor);
        verify(repository).save(fornecedor);
    }

    @Test
    @DisplayName("update: lanca RecursoNaoEncontradoException quando nao existe")
    void update_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }
    @Test
    @DisplayName("delete: deleta quando existe")
    void delete_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(fornecedor));

        service.delete(1);

        verify(repository).save(fornecedor);
    }

    @Test
    @DisplayName("delete: lanca RecursoNaoEncontradoException quando nao existe")
    void delete_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
        verify(repository, never()).delete(any(Fornecedor.class));
    }

    @Test
    @DisplayName("listAll: retorna lista vazia quando nao ha fornecedores")
    void listAll_vazia() {
        when(repository.findAll()).thenReturn(List.of());

        assertThat(service.listAll()).isEmpty();
    }
}
