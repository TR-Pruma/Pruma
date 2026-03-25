package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;
import com.br.pruma.application.mapper.EmpresaMapper;
import com.br.pruma.core.domain.Empresa;
import com.br.pruma.core.repository.EmpresaRepository;
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
class EmpresaServiceTest {

    @Mock EmpresaRepository repository;
    @Mock EmpresaMapper mapper;
    @InjectMocks EmpresaService service;

    Empresa empresa;
    EmpresaRequestDTO requestDTO;
    EmpresaResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        empresa     = mock(Empresa.class);
        requestDTO  = mock(EmpresaRequestDTO.class);
        responseDTO = mock(EmpresaResponseDTO.class);
    }

    @Test
    @DisplayName("salvar: persiste e retorna DTO")
    void salvar_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(empresa);
        when(repository.save(empresa)).thenReturn(empresa);
        when(mapper.toResponseDto(empresa)).thenReturn(responseDTO);

        assertThat(service.salvar(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorCnpj: retorna DTO quando CNPJ existe")
    void buscarPorCnpj_encontrado() {
        when(repository.findByCnpj("00.000.000/0001-00")).thenReturn(Optional.of(empresa));
        when(mapper.toResponseDto(empresa)).thenReturn(responseDTO);

        assertThat(service.buscarPorCnpj("00.000.000/0001-00")).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorCnpj: lanca EntityNotFoundException quando CNPJ nao existe")
    void buscarPorCnpj_naoEncontrado() {
        when(repository.findByCnpj("99.999.999/9999-99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorCnpj("99.999.999/9999-99"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99.999.999/9999-99");
    }

    @Test
    @DisplayName("listarTodas: retorna lista mapeada")
    void listarTodas() {
        when(repository.findAll()).thenReturn(List.of(empresa));
        when(mapper.toResponseDto(empresa)).thenReturn(responseDTO);

        assertThat(service.listarTodas()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("deletar: remove quando CNPJ existe")
    void deletar_sucesso() {
        when(repository.findByCnpj("00.000.000/0001-00")).thenReturn(Optional.of(empresa));

        service.deletar("00.000.000/0001-00");

        verify(repository).delete(empresa);
    }

    @Test
    @DisplayName("deletar: lanca EntityNotFoundException quando CNPJ nao existe")
    void deletar_naoEncontrado() {
        when(repository.findByCnpj("99.999.999/9999-99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar("99.999.999/9999-99"))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
