package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ChecklistRequestDTO;
import com.br.pruma.application.dto.response.ChecklistResponseDTO;
import com.br.pruma.application.dto.update.ChecklistUpdateDTO;
import com.br.pruma.application.mapper.ChecklistMapper;
import com.br.pruma.application.service.impl.ChecklistServiceImpl;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Checklist;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ChecklistRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChecklistServiceTest {

    @Mock private ChecklistRepository repository;
    @Mock private ProjetoRepository projetoRepository;
    @Mock private ChecklistMapper mapper;
    @InjectMocks private ChecklistServiceImpl service;

    // ── Projeto: @SuperBuilder       → Projeto.builder().build()
    // ── Checklist: @Builder          → Checklist.builder().build()
    // ── ChecklistUpdateDTO: @Data    → new ChecklistUpdateDTO() + setters

    private Projeto buildProjeto(Integer id) {
        return Projeto.builder()
                .id(id)
                .nome("Projeto Teste")
                .build();
    }

    private Checklist buildChecklist(Integer id, Projeto projeto) {
        return Checklist.builder()
                .id(id)
                .nome("Checklist Teste")
                .projeto(projeto)
                .ativo(true)
                .build();
    }

    private ChecklistRequestDTO buildRequest() {
        return ChecklistRequestDTO.builder()
                .nome("Checklist Teste")
                .projetoId(1)
                .build();
    }

    private ChecklistResponseDTO buildResponse(Integer id) {
        return ChecklistResponseDTO.builder()
                .id(id)
                .nome("Checklist Teste")
                .projetoId(1)
                .projetoNome("Projeto Teste")
                .ativo(true)
                .build();
    }

    // ── create ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create: salva checklist vinculando projeto e retorna response")
    void create_deveSalvarERetornarResponse() {
        var request  = buildRequest();
        var projeto  = buildProjeto(1);
        var entity   = buildChecklist(null, projeto);
        var saved    = buildChecklist(1, projeto);
        var response = buildResponse(1);

        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        assertThat(service.create(request)).isEqualTo(response);
        verify(repository).save(argThat(c -> c.getProjeto().equals(projeto)));
    }

    @Test
    @DisplayName("create: lança exceção quando projeto não existe")
    void create_deveLancarExcecaoQuandoProjetoNaoEncontrado() {
        when(projetoRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(buildRequest()))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("1");

        verify(repository, never()).save(any());
    }

    // ── getById ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getById: retorna response quando checklist existe")
    void getById_deveRetornarResponse() {
        var entity   = buildChecklist(1, buildProjeto(1));
        var response = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.getById(1)).isEqualTo(response);
    }

    @Test
    @DisplayName("getById: lança exceção quando checklist não existe")
    void getById_deveLancarExcecaoQuandoNaoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }

    // ── listAll ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("listAll: retorna lista mapeada de todos os checklists")
    void listAll_deveRetornarListaMapeada() {
        var entity   = buildChecklist(1, buildProjeto(1));
        var response = buildResponse(1);

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.listAll()).hasSize(1).containsExactly(response);
    }

    // ── list (paginado) ──────────────────────────────────────────────────────

    @Test
    @DisplayName("list: retorna página mapeada")
    void list_deveRetornarPaginaMapeada() {
        var pageable = PageRequest.of(0, 10);
        var entity   = buildChecklist(1, buildProjeto(1));
        var response = buildResponse(1);
        Page<Checklist> page = new PageImpl<>(List.of(entity));

        when(repository.findAll(pageable)).thenReturn(page);
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.list(pageable).getContent()).containsExactly(response);
    }

    // ── listByProjeto ────────────────────────────────────────────────────────

    @Test
    @DisplayName("listByProjeto: retorna checklists filtrados pelo projeto")
    void listByProjeto_deveRetornarChecklistsDoProjeto() {
        var entity   = buildChecklist(1, buildProjeto(1));
        var response = buildResponse(1);

        when(repository.findByProjetoIdWithItens(1)).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.listByProjeto(1)).hasSize(1).containsExactly(response);
        verify(repository).findByProjetoIdWithItens(1);
    }

    // ── update ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update: atualiza campos e retorna response")
    void update_deveAtualizarERetornarResponse() {
        var updateDTO = new ChecklistUpdateDTO();   // @Data sem @Builder → new + setter
        updateDTO.setNome("Novo Nome");

        var entity   = buildChecklist(1, buildProjeto(1));
        var response = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.update(1, updateDTO)).isEqualTo(response);
        verify(mapper).updateFromDto(updateDTO, entity);
    }

    @Test
    @DisplayName("update: lança exceção quando checklist não existe")
    void update_deveLancarExcecaoQuandoNaoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, new ChecklistUpdateDTO()))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }

    // ── replace ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("replace: substitui entidade inteira mantendo ID")
    void replace_deveSubstituirEntidadeMantendoId() {
        var request  = buildRequest();
        var projeto  = buildProjeto(1);
        var entity   = buildChecklist(null, projeto);
        var saved    = buildChecklist(1, projeto);
        var response = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(buildChecklist(1, projeto)));
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(any())).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        assertThat(service.replace(1, request)).isEqualTo(response);
        verify(repository).save(argThat(c -> c.getId().equals(1) && c.getProjeto().equals(projeto)));
    }

    @Test
    @DisplayName("replace: lança exceção quando checklist não existe")
    void replace_deveLancarExcecaoQuandoNaoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, buildRequest()))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }

    // ── delete (soft) ────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete: marca ativo=false (soft delete) sem remover do banco")
    void delete_deveFazerSoftDelete() {
        var entity = buildChecklist(1, buildProjeto(1));
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        service.delete(1);

        verify(repository).save(argThat(c -> !c.isAtivo()));
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("delete: lança exceção quando checklist não existe")
    void delete_deveLancarExcecaoQuandoNaoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }
}