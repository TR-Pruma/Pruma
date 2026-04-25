package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.RequisicaoMaterialRequestDTO;
import com.br.pruma.application.dto.response.RequisicaoMaterialResponseDTO;
import com.br.pruma.application.dto.update.RequisicaoMaterialUpdateDTO;
import com.br.pruma.application.mapper.RequisicaoMaterialMapper;
import com.br.pruma.application.service.impl.RequisicaoMaterialServiceImpl;
import com.br.pruma.core.domain.RequisicaoMaterial;
import com.br.pruma.core.exception.RecursoNaoEncontradoException;
import com.br.pruma.core.repository.port.RequisicaoMaterialRepositoryPort;
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
@DisplayName("RequisicaoMaterialService — testes unitários")
class RequisicaoMaterialServiceTest {

    @Mock private RequisicaoMaterialRepositoryPort repositoryPort;
    @Mock private RequisicaoMaterialMapper mapper;
    @InjectMocks private RequisicaoMaterialServiceImpl service;

    // ── RequisicaoMaterial:        @Builder + @NoArgsConstructor(PROTECTED) + @AllArgsConstructor(PRIVATE)
    //                               → somente RequisicaoMaterial.builder().build() — new proibido
    //                               → não estende AuditableEntity — sem campo ativo
    // ── RequisicaoMaterialRequestDTO:  record → new RequisicaoMaterialRequestDTO(...) — NUNCA mock()
    // ── RequisicaoMaterialResponseDTO: record → new RequisicaoMaterialResponseDTO(...) — NUNCA mock()
    // ── RequisicaoMaterialUpdateDTO:   @Data  → new + setters

    private static final LocalDate DATA = LocalDate.now().plusDays(1);

    private RequisicaoMaterial buildRequisicao(Integer id) {
        return RequisicaoMaterial.builder()
                .id(id)
                .quantidade(10)
                .dataRequisicao(DATA)
                .build();
    }

    private RequisicaoMaterialRequestDTO buildRequest() {
        return new RequisicaoMaterialRequestDTO(1, 10, 100, DATA);
    }

    private RequisicaoMaterialResponseDTO buildResponse() {
        return new RequisicaoMaterialResponseDTO(1, 1, "Obra Teste", 10, "Cimento", 100, DATA, DATA);
    }

    // ── create ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create: salva via port e retorna DTO")
    void create_sucesso() {
        var req      = buildRequest();
        var entity   = buildRequisicao(null);
        var saved    = buildRequisicao(1);
        var response = buildResponse();

        when(mapper.toEntity(req)).thenReturn(entity);
        when(repositoryPort.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        assertThat(service.create(req)).isEqualTo(response);
        verify(repositoryPort).save(entity);
    }

    // ── getById ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getById: retorna DTO quando requisição existe")
    void getById_encontrado() {
        var entity   = buildRequisicao(1);
        var response = buildResponse();

        when(repositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.getById(1)).isEqualTo(response);
    }

    @Test
    @DisplayName("getById: não encontrado lança RecursoNaoEncontradoException")
    void getById_naoEncontrado() {
        when(repositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }

    // ── listAll ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("listAll: retorna lista de DTOs")
    void listAll_comRegistros() {
        var entity   = buildRequisicao(1);
        var response = buildResponse();

        when(repositoryPort.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.listAll()).hasSize(1).containsExactly(response);
    }

    @Test
    @DisplayName("listAll: retorna lista vazia")
    void listAll_vazio() {
        when(repositoryPort.findAll()).thenReturn(List.of());
        assertThat(service.listAll()).isEmpty();
    }

    // ── listByProjeto ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("listByProjeto: retorna requisições do projeto informado")
    void listByProjeto_comRegistros() {
        var entity   = buildRequisicao(1);
        var response = buildResponse();

        when(repositoryPort.findByProjetoId(10)).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.listByProjeto(10)).hasSize(1).containsExactly(response);
        verify(repositoryPort).findByProjetoId(10);
    }

    @Test
    @DisplayName("listByProjeto: retorna lista vazia quando não há requisições")
    void listByProjeto_vazio() {
        when(repositoryPort.findByProjetoId(10)).thenReturn(List.of());
        assertThat(service.listByProjeto(10)).isEmpty();
    }

    // ── update ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update: chama mapper.updateEntity e salva via port")
    void update_sucesso() {
        // @Data sem @Builder → new + setter
        var upd = new RequisicaoMaterialUpdateDTO();
        upd.setQuantidade(java.math.BigDecimal.valueOf(20));

        var entity   = buildRequisicao(1);
        var response = buildResponse();

        when(repositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(repositoryPort.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.update(1, upd)).isEqualTo(response);
        verify(mapper).updateEntity(upd, entity);
    }

    @Test
    @DisplayName("update: não encontrado lança RecursoNaoEncontradoException")
    void update_naoEncontrado() {
        when(repositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, new RequisicaoMaterialUpdateDTO()))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    // ── replace ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("replace: substitui requisição completa mantendo o mesmo ID")
    void replace_sucesso() {
        var req      = buildRequest();
        var nova     = buildRequisicao(null);
        var saved    = buildRequisicao(1);
        var response = buildResponse();

        when(repositoryPort.findById(1)).thenReturn(Optional.of(buildRequisicao(1)));
        when(mapper.toEntity(req)).thenReturn(nova);
        when(repositoryPort.save(any())).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        assertThat(service.replace(1, req)).isEqualTo(response);
        verify(repositoryPort).save(argThat(r -> r.getId().equals(1)));
    }

    @Test
    @DisplayName("replace: não encontrado lança RecursoNaoEncontradoException")
    void replace_naoEncontrado() {
        when(repositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, buildRequest()))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    // ── delete ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete: chama deleteById após encontrar a entidade")
    void delete_sucesso() {
        when(repositoryPort.findById(1)).thenReturn(Optional.of(buildRequisicao(1)));

        assertThatCode(() -> service.delete(1)).doesNotThrowAnyException();
        verify(repositoryPort).deleteById(1);
    }

    @Test
    @DisplayName("delete: não encontrado lança RecursoNaoEncontradoException")
    void delete_naoEncontrado() {
        when(repositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}