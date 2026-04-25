package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import com.br.pruma.application.dto.update.CronogramaUpdateDTO;
import com.br.pruma.application.mapper.CronogramaMapper;
import com.br.pruma.application.service.impl.CronogramaServiceImpl;
import com.br.pruma.core.domain.Cronograma;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.CronogramaRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CronogramaService — testes unitários")
class CronogramaServiceTest {

    @Mock private CronogramaRepository repository;
    @Mock private ProjetoRepository projetoRepository;
    @Mock private CronogramaMapper mapper;
    @InjectMocks private CronogramaServiceImpl service;

    // ── Projeto:              @SuperBuilder → Projeto.builder().build()
    // ── Cronograma:           @SuperBuilder → Cronograma.builder().build() — ativo = Boolean (getAtivo())
    // ── CronogramaRequestDTO: record        → new CronogramaRequestDTO(...) — NUNCA mock()
    // ── CronogramaResponseDTO: record       → new CronogramaResponseDTO(...) — NUNCA mock()
    // ── CronogramaUpdateDTO:  @Data         → new + setters

    private static final LocalDate DATA_INICIO = LocalDate.now().plusDays(1);
    private static final LocalDate DATA_FIM    = LocalDate.now().plusMonths(3);

    private Projeto buildProjeto(Integer id) {
        return Projeto.builder()
                .id(id)
                .nome("Projeto Teste")
                .build();
    }

    private Cronograma buildCronograma(Integer id, Projeto projeto) {
        return Cronograma.builder()
                .id(id)
                .nome("Cronograma Teste")
                .projeto(projeto)
                .dataInicio(DATA_INICIO)
                .dataFim(DATA_FIM)
                .ativo(true)
                .build();
    }

    private CronogramaRequestDTO buildRequest(Integer projetoId) {
        return new CronogramaRequestDTO(projetoId, DATA_INICIO, DATA_FIM);
    }

    private CronogramaResponseDTO buildResponse(Integer id) {
        return new CronogramaResponseDTO(id, 10, DATA_INICIO, DATA_FIM);
    }

    // ── create ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create: busca projeto, salva e retorna DTO")
    void create_sucesso() {
        var req      = buildRequest(10);
        var projeto  = buildProjeto(10);
        var entity   = buildCronograma(null, projeto);
        var saved    = buildCronograma(1, projeto);
        var response = buildResponse(1);

        when(projetoRepository.findById(10)).thenReturn(Optional.of(projeto));
        when(mapper.toEntity(req)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        assertThat(service.create(req)).isEqualTo(response);
        verify(repository).save(argThat(c -> c.getProjeto().equals(projeto)));
    }

    @Test
    @DisplayName("create: projeto não encontrado lança EntityNotFoundException")
    void create_projetoNaoEncontrado() {
        when(projetoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(buildRequest(99)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");

        verify(repository, never()).save(any());
    }

    // ── getById ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getById: retorna response quando cronograma existe")
    void getById_encontrado() {
        var entity   = buildCronograma(1, buildProjeto(10));
        var response = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.getById(1)).isEqualTo(response);
    }

    @Test
    @DisplayName("getById: não encontrado lança EntityNotFoundException")
    void getById_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ── listAll ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll_comRegistros() {
        var entity   = buildCronograma(1, buildProjeto(10));
        var response = buildResponse(1);

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.listAll()).hasSize(1).containsExactly(response);
    }

    // ── listByProjeto ────────────────────────────────────────────────────────

    @Test
    @DisplayName("listByProjeto: retorna cronogramas do projeto")
    void listByProjeto_comRegistros() {
        var entity   = buildCronograma(1, buildProjeto(10));
        var response = buildResponse(1);

        when(repository.findAllByProjetoId(10)).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.listByProjeto(10)).hasSize(1).containsExactly(response);
        verify(repository).findAllByProjetoId(10);
    }

    // ── update ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update: não troca projeto quando projetoId for null")
    void update_semTrocarProjeto() {
        var upd      = new CronogramaUpdateDTO();   // projetoId = null
        var entity   = buildCronograma(1, buildProjeto(10));
        var response = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.update(1, upd)).isEqualTo(response);
        verify(projetoRepository, never()).findById(any());
        verify(mapper).updateFromDto(upd, entity);
    }

    @Test
    @DisplayName("update: troca projeto quando projetoId não for null")
    void update_comTrocaDeProjeto() {
        var upd = new CronogramaUpdateDTO();
        upd.setProjetoId(10);

        var novoProjeto = buildProjeto(10);
        var entity      = buildCronograma(1, buildProjeto(99));
        var response    = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(projetoRepository.findById(10)).thenReturn(Optional.of(novoProjeto));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        service.update(1, upd);

        assertThat(entity.getProjeto()).isEqualTo(novoProjeto);
    }

    @Test
    @DisplayName("update: não encontrado lança EntityNotFoundException")
    void update_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, new CronogramaUpdateDTO()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ── delete (soft) ────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete: marca ativo=false sem remover do banco")
    void delete_softDelete() {
        var entity = buildCronograma(1, buildProjeto(10));
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        service.delete(1);

        // AuditableEntity.ativo é Boolean (wrapper) → getter é getAtivo(), não isAtivo()
        verify(repository).save(argThat(c -> !c.getAtivo()));
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("delete: não encontrado lança EntityNotFoundException")
    void delete_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }
}