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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitacaoMudancaServiceTest {

    @Mock SolicitacaoMudancaRepository repository;
    @Mock SolicitacaoMudancaMapper mapper;
    @Mock ProjetoRepository projetoRepository;
    @Mock StatusSolicitacaoRepository statusSolicitacaoRepository;
    @InjectMocks SolicitacaoMudancaServiceImpl service;

    SolicitacaoMudanca entity;
    SolicitacaoMudancaResponseDTO responseDTO;
    Projeto projeto;
    StatusSolicitacao status;

    @BeforeEach
    void setUp() {
        entity      = mock(SolicitacaoMudanca.class);
        responseDTO = mock(SolicitacaoMudancaResponseDTO.class);
        projeto     = mock(Projeto.class);
        status      = mock(StatusSolicitacao.class);
    }

    // ─── create ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create: salva com projeto e status quando ambos existem")
    void create_sucesso() {
        var dto = mock(SolicitacaoMudancaRequestDTO.class);
        when(dto.projetoId()).thenReturn(1);
        when(dto.statusSolicitacaoId()).thenReturn(2);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(statusSolicitacaoRepository.findById(2)).thenReturn(Optional.of(status));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.create(dto)).isEqualTo(responseDTO);
        verify(entity).setProjeto(projeto);
        verify(entity).setStatusSolicitacao(status);
    }

    @Test
    @DisplayName("create: lanca EntityNotFoundException quando projeto nao existe")
    void create_projetoNaoEncontrado() {
        var dto = mock(SolicitacaoMudancaRequestDTO.class);
        when(dto.projetoId()).thenReturn(99);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(projetoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("create: lanca EntityNotFoundException quando status nao existe")
    void create_statusNaoEncontrado() {
        var dto = mock(SolicitacaoMudancaRequestDTO.class);
        when(dto.projetoId()).thenReturn(1);
        when(dto.statusSolicitacaoId()).thenReturn(99);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(statusSolicitacaoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    // ─── getById ───────────────────────────────────────────────────────────────

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
                .isInstanceOf(EntityNotFoundException.class);
    }

    // ─── list ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("list: retorna pagina mapeada")
    void list_paginado() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.list(pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listByProjeto: retorna lista filtrada")
    void listByProjeto() {
        when(repository.findByProjetoId(3)).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listByProjeto(3)).containsExactly(responseDTO);
    }

    // ─── update ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update: atualiza sem trocar projeto nem status quando ids null")
    void update_semTrocas() {
        var updateDTO = mock(SolicitacaoMudancaUpdateDTO.class);
        when(updateDTO.projetoId()).thenReturn(null);
        when(updateDTO.statusSolicitacaoId()).thenReturn(null);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verifyNoInteractions(projetoRepository);
        verifyNoInteractions(statusSolicitacaoRepository);
        verify(mapper).updateFromDto(updateDTO, entity);
    }

    @Test
    @DisplayName("update: troca projeto e status quando ids fornecidos")
    void update_comTrocas() {
        var updateDTO = mock(SolicitacaoMudancaUpdateDTO.class);
        when(updateDTO.projetoId()).thenReturn(5);
        when(updateDTO.statusSolicitacaoId()).thenReturn(6);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(projetoRepository.findById(5)).thenReturn(Optional.of(projeto));
        when(statusSolicitacaoRepository.findById(6)).thenReturn(Optional.of(status));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        service.update(1, updateDTO);

        verify(entity).setProjeto(projeto);
        verify(entity).setStatusSolicitacao(status);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(SolicitacaoMudancaUpdateDTO.class);
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    // ─── replace ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("replace: substitui com projeto e status quando existem")
    void replace_sucesso() {
        var dto = mock(SolicitacaoMudancaRequestDTO.class);
        when(dto.projetoId()).thenReturn(1);
        when(dto.statusSolicitacaoId()).thenReturn(2);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(statusSolicitacaoRepository.findById(2)).thenReturn(Optional.of(status));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.replace(1, dto)).isEqualTo(responseDTO);
        verify(entity).setId(1);
        verify(entity).setProjeto(projeto);
        verify(entity).setStatusSolicitacao(status);
    }

    @Test
    @DisplayName("replace: lanca EntityNotFoundException quando solicitacao nao existe")
    void replace_naoEncontrado() {
        var dto = mock(SolicitacaoMudancaRequestDTO.class);
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    // ─── delete ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete: soft-delete quando existe")
    void delete_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        service.delete(1);

        verify(entity).setAtivo(false);
        verify(repository).save(entity);
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
