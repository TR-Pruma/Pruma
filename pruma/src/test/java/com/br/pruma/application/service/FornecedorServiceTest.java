package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.FornecedorRequestDTO;
import com.br.pruma.application.dto.response.FornecedorResponseDTO;
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
    @DisplayName("salvar: salva e retorna DTO")
    void salvar_sucesso() {
        when(requestDTO.getCnpj()).thenReturn("00000000000000");
        when(repository.existsByCnpj("00000000000000")).thenReturn(false);
        when(mapper.toEntity(requestDTO)).thenReturn(fornecedor);
        when(repository.save(fornecedor)).thenReturn(fornecedor);
        when(mapper.toResponse(fornecedor)).thenReturn(responseDTO);

        assertThat(service.salvar(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("salvar: lanca excecao quando CNPJ ja existe")
    void salvar_cnpjDuplicado() {
        when(requestDTO.getCnpj()).thenReturn("00000000000000");
        when(repository.existsByCnpj("00000000000000")).thenReturn(true);

        assertThatThrownBy(() -> service.salvar(requestDTO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("buscarPorId: retorna DTO quando existe")
    void buscarPorId_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(fornecedor));
        when(mapper.toResponse(fornecedor)).thenReturn(responseDTO);

        assertThat(service.buscarPorId(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorId: lanca EntityNotFoundException quando nao existe")
    void buscarPorId_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("listarTodos: retorna lista mapeada")
    void listarTodos() {
        when(repository.findAll()).thenReturn(List.of(fornecedor));
        when(mapper.toResponseList(List.of(fornecedor))).thenReturn(List.of(responseDTO));

        assertThat(service.listarTodos()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("atualizar: atualiza quando existe")
    void atualizar_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(fornecedor));
        when(fornecedor.getCnpj()).thenReturn("00000000000000");
        when(requestDTO.getCnpj()).thenReturn("00000000000000");
        when(repository.save(fornecedor)).thenReturn(fornecedor);
        when(mapper.toResponse(fornecedor)).thenReturn(responseDTO);

        assertThat(service.atualizar(1, requestDTO)).isEqualTo(responseDTO);
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
        when(repository.existsById(1)).thenReturn(true);
        service.deletar(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("deletar: lanca EntityNotFoundException quando nao existe")
    void deletar_naoEncontrado() {
        when(repository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(EntityNotFoundException.class);
        verify(repository, never()).deleteById(any());
    }
}
