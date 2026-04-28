package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;
import com.br.pruma.application.dto.update.EmpresaUpdateDTO;
import com.br.pruma.application.mapper.EmpresaMapper;
import com.br.pruma.application.service.impl.EmpresaServiceImpl;
import com.br.pruma.core.domain.Empresa;
import com.br.pruma.core.repository.EmpresaRepository;
import com.br.pruma.config.RecursoNaoEncontradoException;
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
    @InjectMocks EmpresaServiceImpl service;

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
    @DisplayName("create: persiste e retorna DTO")
    void create_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(empresa);
        when(repository.save(empresa)).thenReturn(empresa);
        when(mapper.toResponseDto(empresa)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando CNPJ existe")
    void getById_encontrado() {
        when(repository.findByCnpj("00.000.000/0001-00")).thenReturn(Optional.of(empresa));
        when(mapper.toResponseDto(empresa)).thenReturn(responseDTO);

        assertThat(service.getById("00.000.000/0001-00")).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca RecursoNaoEncontradoException quando CNPJ nao existe")
    void getById_naoEncontrado() {
        when(repository.findByCnpj("99.999.999/9999-99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById("99.999.999/9999-99"))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99.999.999/9999-99");
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(repository.findAll()).thenReturn(List.of(empresa));
        when(mapper.toResponseDto(empresa)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("delete: remove quando CNPJ existe")
    void delete_sucesso() {
        when(repository.findByCnpj("00.000.000/0001-00")).thenReturn(Optional.of(empresa));

        service.delete("00.000.000/0001-00");

        verify(repository).deleteByCnpj("00.000.000/0001-00");
    }

    @Test
    @DisplayName("delete: lanca RecursoNaoEncontradoException quando CNPJ nao existe")
    void delete_naoEncontrado() {
        when(repository.findByCnpj("99.999.999/9999-99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete("99.999.999/9999-99"))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("listAll: retorna lista vazia quando nao ha empresas")
    void listAll_vazia() {
        when(repository.findAll()).thenReturn(List.of());

        assertThat(service.listAll()).isEmpty();
    }

    @Test
    @DisplayName("update: atualiza quando CNPJ existe")
    void update_sucesso() {
        var updateDTO = mock(EmpresaUpdateDTO.class);
        when(repository.findByCnpj("00.000.000/0001-00")).thenReturn(Optional.of(empresa));
        when(repository.save(empresa)).thenReturn(empresa);
        when(mapper.toResponseDto(empresa)).thenReturn(responseDTO);

        assertThat(service.update("00.000.000/0001-00", updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, empresa);
        verify(repository).save(empresa);
    }

    @Test
    @DisplayName("update: lanca RecursoNaoEncontradoException quando CNPJ nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(EmpresaUpdateDTO.class);
        when(repository.findByCnpj("99.999.999/9999-99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update("99.999.999/9999-99", updateDTO))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99.999.999/9999-99");
    }
}
