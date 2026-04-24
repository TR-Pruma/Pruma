package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.SolicitacaoMudancaRequestDTO;
import com.br.pruma.application.dto.response.SolicitacaoMudancaResponseDTO;
import com.br.pruma.application.dto.update.SolicitacaoMudancaUpdateDTO;
import com.br.pruma.application.mapper.SolicitacaoMudancaMapper;
import com.br.pruma.application.service.impl.SolicitacaoMudancaServiceImpl;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.domain.SolicitacaoMudanca;
import com.br.pruma.core.domain.StatusSolicitacao;
import com.br.pruma.core.repository.ProjetoRepository;
import com.br.pruma.core.repository.SolicitacaoMudancaRepository;
import com.br.pruma.core.repository.StatusSolicitacaoRepository;
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
class SolicitacaoMudancaServiceTest {

    @Mock SolicitacaoMudancaRepository repository;
    @Mock SolicitacaoMudancaMapper mapper;
    @Mock ProjetoRepository projetoRepository;
    @Mock StatusSolicitacaoRepository statusSolicitacaoRepository;
    @InjectMocks SolicitacaoMudancaServiceImpl service;

    SolicitacaoMudanca entity;
    SolicitacaoMudancaRequestDTO requestDTO;
    SolicitacaoMudancaUpdateDTO updateDTO;
    SolicitacaoMudancaResponseDTO responseDTO;
    Projeto projeto;
    StatusSolicitacao status;

    @BeforeEach
    void setUp() {
        entity      = mock(SolicitacaoMudanca.class);
        requestDTO  = mock(SolicitacaoMudancaRequestDTO.class);
        updateDTO   = mock(SolicitacaoMudancaUpdateDTO.class);
        responseDTO = mock(SolicitacaoMudancaResponseDTO.class);
        projeto     = mock(Projeto.class);
        status      = mock(StatusSolicitacao.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        when(requestDTO.projetoId()).thenReturn(1);
        when(requestDTO.statusSolicitacaoId()).thenReturn(1);
        when(mapper.toEntity(requestDTO)).thenReturn(entity);
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(statusSolicitacaoRepository.findById(1)).thenReturn(Optional.of(status));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
        verify(entity).setProjeto(projeto);
        verify(entity).setStatusSolicitacao(status);
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("create: lanca EntityNotFoundException quando projeto nao existe")
    void create_projetoNaoEncontrado() {
        when(requestDTO.projetoId()).thenReturn(99);
        when(mapper.toEntity(requestDTO)).thenReturn(entity);
        when(projetoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(requestDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

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
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listByProjeto: retorna lista filtrada por projeto")
    void listByProjeto() {
        when(repository.findByProjetoId(1)).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listByProjeto(1)).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza campos simples e associacoes quando existem")
    void update_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(updateDTO.projetoId()).thenReturn(null);
        when(updateDTO.statusSolicitacaoId()).thenReturn(null);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, entity);
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("delete: deleta quando existe")
    void delete_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        service.delete(1);

        verify(repository).save(entity);
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
        verify(repository, never()).delete(any(SolicitacaoMudanca.class));
    }
}