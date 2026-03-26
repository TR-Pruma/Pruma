package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.OrcamentoRequestDTO;
import com.br.pruma.application.dto.response.OrcamentoResponseDTO;
import com.br.pruma.application.dto.update.OrcamentoUpdateDTO;
import com.br.pruma.application.mapper.OrcamentoMapper;
import com.br.pruma.core.domain.Empresa;
import com.br.pruma.core.domain.Orcamento;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.EmpresaRepository;
import com.br.pruma.core.repository.OrcamentoRepository;
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
class OrcamentoServiceTest {

    @Mock OrcamentoRepository orcamentoRepository;
    @Mock ProjetoRepository projetoRepository;
    @Mock EmpresaRepository empresaRepository;
    @Mock OrcamentoMapper mapper;
    @InjectMocks OrcamentoService service;

    Orcamento orcamento;
    OrcamentoRequestDTO requestDTO;
    OrcamentoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        orcamento   = mock(Orcamento.class);
        requestDTO  = mock(OrcamentoRequestDTO.class);
        responseDTO = mock(OrcamentoResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        when(requestDTO.getProjetoId()).thenReturn(1);
        when(requestDTO.getEmpresaCnpj()).thenReturn("00000000000000");
        when(projetoRepository.findById(1)).thenReturn(Optional.of(mock(Projeto.class)));
        when(empresaRepository.findById("00000000000000")).thenReturn(Optional.of(mock(Empresa.class)));
        when(mapper.toEntity(requestDTO)).thenReturn(orcamento);
        when(orcamentoRepository.save(orcamento)).thenReturn(orcamento);
        when(mapper.toResponse(orcamento)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(orcamentoRepository.findById(1)).thenReturn(Optional.of(orcamento));
        when(mapper.toResponse(orcamento)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca EntityNotFoundException quando nao existe")
    void getById_naoEncontrado() {
        when(orcamentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(orcamentoRepository.findAll()).thenReturn(List.of(orcamento));
        when(mapper.toResponse(orcamento)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(OrcamentoUpdateDTO.class);
        when(updateDTO.getProjetoId()).thenReturn(null);
        when(updateDTO.getEmpresaCnpj()).thenReturn(null);
        when(orcamentoRepository.findById(1)).thenReturn(Optional.of(orcamento));
        when(orcamentoRepository.save(orcamento)).thenReturn(orcamento);
        when(mapper.toResponse(orcamento)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, orcamento);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(OrcamentoUpdateDTO.class);
        when(updateDTO.getProjetoId()).thenReturn(null);
        when(orcamentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("delete: deleta quando existe")
    void delete_sucesso() {
        when(orcamentoRepository.existsById(1)).thenReturn(true);
        service.delete(1);
        verify(orcamentoRepository).deleteById(1);
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(orcamentoRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class);
        verify(orcamentoRepository, never()).deleteById(any());
    }
}
