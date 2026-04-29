package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.HistoricoLocalizacaoRequestDTO;
import com.br.pruma.application.dto.response.HistoricoLocalizacaoResponseDTO;
import com.br.pruma.application.mapper.HistoricoLocalizacaoMapper;
import com.br.pruma.application.service.impl.HistoricoLocalizacaoServiceImpl;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.HistoricoLocalizacao;
import com.br.pruma.core.domain.ProfissionalDeBase;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.HistoricoLocalizacaoRepository;
import com.br.pruma.core.repository.ProfissionalDeBaseRepository;
import com.br.pruma.core.repository.ProjetoRepository;
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
class HistoricoLocalizacaoServiceTest {

    @Mock HistoricoLocalizacaoRepository repository;
    @Mock ProfissionalDeBaseRepository profissionalRepository;
    @Mock ProjetoRepository projetoRepository;
    @Mock HistoricoLocalizacaoMapper mapper;
    @InjectMocks HistoricoLocalizacaoServiceImpl service;

    HistoricoLocalizacao entity;
    HistoricoLocalizacaoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(HistoricoLocalizacao.class);
        responseDTO = mock(HistoricoLocalizacaoResponseDTO.class);
    }

    @Test
    @DisplayName("salvar: persiste e retorna DTO")
    void salvar_sucesso() {
        var dto          = mock(HistoricoLocalizacaoRequestDTO.class);
        var profissional = mock(ProfissionalDeBase.class);
        var projeto      = mock(Projeto.class);
        when(dto.profissionalCpf()).thenReturn(12345678901L);
        when(dto.projetoId()).thenReturn(1);
        when(profissionalRepository.findByCpf("12345678901")).thenReturn(Optional.of(profissional));
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(mapper.toEntity(dto, profissional, projeto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.salvar(dto)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("salvar: lanca excecao quando profissional nao encontrado")
    void salvar_profissionalNaoEncontrado() {
        var dto = mock(HistoricoLocalizacaoRequestDTO.class);
        when(dto.profissionalCpf()).thenReturn(99999999999L);
        when(profissionalRepository.findByCpf("99999999999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.salvar(dto))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("salvar: lanca excecao quando projeto nao encontrado")
    void salvar_projetoNaoEncontrado() {
        var dto          = mock(HistoricoLocalizacaoRequestDTO.class);
        var profissional = mock(ProfissionalDeBase.class);
        when(dto.profissionalCpf()).thenReturn(12345678901L);
        when(dto.projetoId()).thenReturn(99);
        when(profissionalRepository.findByCpf("12345678901")).thenReturn(Optional.of(profissional));
        when(projetoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.salvar(dto))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("buscarPorId: retorna DTO quando existe")
    void buscarPorId_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.buscarPorId(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorId: lanca excecao quando nao existe")
    void buscarPorId_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("listarTodos: retorna lista mapeada")
    void listarTodos() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listarTodos()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listarTodos: retorna lista vazia quando nao ha registros")
    void listarTodos_vazia() {
        when(repository.findAll()).thenReturn(List.of());

        assertThat(service.listarTodos()).isEmpty();
    }

    @Test
    @DisplayName("deletar: soft-delete quando existe")
    void deletar_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        service.deletar(1);

        verify(entity).setAtivo(false);
        verify(repository).save(entity);
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("deletar: lanca excecao quando nao existe")
    void deletar_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}
