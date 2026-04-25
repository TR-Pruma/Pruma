package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.InspecaoRequestDTO;
import com.br.pruma.application.dto.response.InspecaoResponseDTO;
import com.br.pruma.application.dto.update.InspecaoUpdateDTO;
import com.br.pruma.application.mapper.InspecaoMapper;
import com.br.pruma.application.service.impl.InspecaoServiceImpl;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Inspecao;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.InspecaoRepository;
import com.br.pruma.core.repository.ProjetoRepository;
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
@DisplayName("InspecaoService — testes unitários")
class InspecaoServiceTest {

    @Mock private InspecaoRepository repository;
    @Mock private ProjetoRepository projetoRepository;
    @Mock private InspecaoMapper mapper;
    @InjectMocks private InspecaoServiceImpl service;

    private static final LocalDate DATA = LocalDate.now().plusDays(1);

    private Projeto buildProjeto(Integer id) {
        return Projeto.builder()
                .id(id)
                .nome("Projeto Teste")
                .build();
    }

    private Inspecao buildInspecao(Integer id, Projeto projeto) {
        // @Builder simples não herda AuditableEntity → setar ativo via setter
        var inspecao = Inspecao.builder()
                .id(id)
                .projeto(projeto)
                .descricao("Descrição Teste")
                .dataInspecao(DATA)
                .resultado("Aprovado")
                .build();
        inspecao.setAtivo(true);
        return inspecao;
    }

    private InspecaoRequestDTO buildRequest(Integer projetoId) {
        // record → instanciar diretamente, nunca mock()
        return new InspecaoRequestDTO(projetoId, 12345678901L, "Descrição Teste", DATA, "Aprovado");
    }

    private InspecaoResponseDTO buildResponse(Integer id) {
        // record → instanciar diretamente, nunca mock()
        return new InspecaoResponseDTO(id, 10, 12345678901L, "Descrição Teste", DATA, "Aprovado");
    }

    // ── create ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create: busca projeto, salva e retorna DTO")
    void create_sucesso() {
        var req      = buildRequest(10);
        var projeto  = buildProjeto(10);
        var entity   = buildInspecao(null, projeto);
        var saved    = buildInspecao(1, projeto);
        var response = buildResponse(1);

        when(projetoRepository.findById(10)).thenReturn(Optional.of(projeto));
        when(mapper.toEntity(req)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponseDTO(saved)).thenReturn(response);

        assertThat(service.create(req)).isEqualTo(response);
        verify(repository).save(argThat(i -> i.getProjeto().equals(projeto)));
    }

    @Test
    @DisplayName("create: projeto não encontrado lança RecursoNaoEncontradoException")
    void create_projetoNaoEncontrado() {
        when(projetoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(buildRequest(99)))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");

        verify(repository, never()).save(any());
    }

    // ── getById ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getById: retorna DTO quando inspeção existe")
    void getById_encontrado() {
        var entity   = buildInspecao(1, buildProjeto(10));
        var response = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(response);

        assertThat(service.getById(1)).isEqualTo(response);
    }

    @Test
    @DisplayName("getById: não encontrado lança RecursoNaoEncontradoException")
    void getById_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }

    // ── listAll ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("listAll: retorna lista completa")
    void listAll_comRegistros() {
        var entity   = buildInspecao(1, buildProjeto(10));
        var response = buildResponse(1);

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(response);

        assertThat(service.listAll()).hasSize(1).containsExactly(response);
    }

    @Test
    @DisplayName("listAll: retorna lista vazia")
    void listAll_vazio() {
        when(repository.findAll()).thenReturn(List.of());
        assertThat(service.listAll()).isEmpty();
    }

    // ── listByProjeto ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("listByProjeto: retorna inspeções do projeto")
    void listByProjeto_comRegistros() {
        var entity   = buildInspecao(1, buildProjeto(10));
        var response = buildResponse(1);

        when(repository.findAllByProjeto_Id(10)).thenReturn(List.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(response);

        assertThat(service.listByProjeto(10)).hasSize(1).containsExactly(response);
        verify(repository).findAllByProjeto_Id(10);
    }

    @Test
    @DisplayName("listByProjeto: retorna lista vazia quando não há inspeções")
    void listByProjeto_vazio() {
        when(repository.findAllByProjeto_Id(10)).thenReturn(List.of());
        assertThat(service.listByProjeto(10)).isEmpty();
    }

    // ── update ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update: atualiza campos e retorna DTO sem trocar projeto")
    void update_sucesso() {
        var upd      = new InspecaoUpdateDTO();
        upd.setDescricao("Nova Descrição");

        var entity   = buildInspecao(1, buildProjeto(10));
        var response = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(response);

        assertThat(service.update(1, upd)).isEqualTo(response);
        verify(mapper).updateFromDto(upd, entity);
        verify(projetoRepository, never()).findById(any());
    }

    @Test
    @DisplayName("update: não encontrado lança RecursoNaoEncontradoException")
    void update_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, new InspecaoUpdateDTO()))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }

    // ── replace ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("replace: substitui inspeção completa mantendo o mesmo ID")
    void replace_sucesso() {
        var req      = buildRequest(10);
        var projeto  = buildProjeto(10);
        var nova     = buildInspecao(null, projeto);
        var saved    = buildInspecao(1, projeto);
        var response = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(buildInspecao(1, projeto)));
        when(projetoRepository.findById(10)).thenReturn(Optional.of(projeto));
        when(mapper.toEntity(req)).thenReturn(nova);
        when(repository.save(any())).thenReturn(saved);
        when(mapper.toResponseDTO(saved)).thenReturn(response);

        assertThat(service.replace(1, req)).isEqualTo(response);
        verify(repository).save(argThat(i -> i.getId().equals(1) && i.getProjeto().equals(projeto)));
    }

    @Test
    @DisplayName("replace: não encontrado lança RecursoNaoEncontradoException")
    void replace_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, buildRequest(10)))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }

    // ── delete (soft) ────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete: marca ativo=false sem remover do banco")
    void delete_softDelete() {
        var entity = buildInspecao(1, buildProjeto(10));
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        service.delete(1);

        // Boolean wrapper → getAtivo(), não isAtivo()
        verify(repository).save(argThat(i -> !i.getAtivo()));
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("delete: não encontrado lança RecursoNaoEncontradoException")
    void delete_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }
}