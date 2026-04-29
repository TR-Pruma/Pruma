package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ApadrinhamentoRedeRequestDTO;
import com.br.pruma.application.dto.response.ApadrinhamentoRedeResponseDTO;
import com.br.pruma.application.dto.update.ApadrinhamentoRedeUpdateDTO;
import com.br.pruma.application.mapper.ApadrinhamentoRedeMapper;
import com.br.pruma.application.service.impl.ApadrinhamentoRedeServiceImpl;
import com.br.pruma.config.Constantes;
import com.br.pruma.core.domain.ApadrinhamentoRede;
import com.br.pruma.core.domain.ProfissionalDeBase;
import com.br.pruma.core.repository.port.ApadrinhamentoRedeRepositoryPort;
import com.br.pruma.core.repository.port.ProfissionalDeBaseRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApadrinhamentoRedeServiceTest {

    @Mock private ApadrinhamentoRedeRepositoryPort repositoryPort;
    @Mock private ProfissionalDeBaseRepositoryPort profissionalPort;
    @Mock private ApadrinhamentoRedeMapper mapper;
    @InjectMocks private ApadrinhamentoRedeServiceImpl service;

    // ── builders ─────────────────────────────────────────────────────────────

    private ProfissionalDeBase buildProfissional(Long id, String nome) {
        return ProfissionalDeBase.builder()
                .id(id.intValue())
                .nome(nome)
                .build();
    }

    private ApadrinhamentoRede buildEntity(Long id, ProfissionalDeBase padrinho, ProfissionalDeBase afilhado) {
        return ApadrinhamentoRede.builder()
                .id(id)
                .padrinho(padrinho)
                .afilhado(afilhado)
                .dataInicio(LocalDate.of(2024, 1, 15))
                .status("ATIVO")
                .build();
    }

    private ApadrinhamentoRedeRequestDTO buildRequest() {
        return new ApadrinhamentoRedeRequestDTO(1L, 2L, LocalDate.of(2024, 1, 15), null, "ATIVO");
    }

    private ApadrinhamentoRedeResponseDTO buildResponse(Long id) {
        return new ApadrinhamentoRedeResponseDTO(
                id, 1L, "João Silva", 2L, "Maria Souza",
                LocalDate.of(2024, 1, 15), null, "ATIVO",
                LocalDateTime.now(), LocalDateTime.now()
        );
    }

    private ApadrinhamentoRedeUpdateDTO buildUpdateDTO() {
        return new ApadrinhamentoRedeUpdateDTO(LocalDate.of(2025, 6, 30), "ENCERRADO");
    }

    // ── create ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create: salva vínculo vinculando padrinho e afilhado e retorna response")
    void create_deveSalvarERetornarResponse() {
        var request  = buildRequest();
        var padrinho = buildProfissional(1L, "João Silva");
        var afilhado = buildProfissional(2L, "Maria Souza");
        var entity   = buildEntity(null, padrinho, afilhado);
        var saved    = buildEntity(10L, padrinho, afilhado);
        var response = buildResponse(10L);

        when(profissionalPort.findById(1)).thenReturn(Optional.of(padrinho));
        when(profissionalPort.findById(2)).thenReturn(Optional.of(afilhado));
        when(repositoryPort.existsByPadrinhoIdAndAfilhadoIdAndStatus(1L, 2L, "ATIVO")).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repositoryPort.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        assertThat(service.create(request)).isEqualTo(response);
        verify(repositoryPort).save(argThat(e ->
                e.getPadrinho().equals(padrinho) && e.getAfilhado().equals(afilhado)));
    }

    @Test
    @DisplayName("create: lança exceção quando padrinho não existe")
    void create_deveLancarExcecaoQuandoPadrinhoNaoEncontrado() {
        when(profissionalPort.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(buildRequest()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("1");

        verify(repositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("create: lança exceção quando afilhado não existe")
    void create_deveLancarExcecaoQuandoAfilhadoNaoEncontrado() {
        var padrinho = buildProfissional(1L, "João Silva");
        when(profissionalPort.findById(1)).thenReturn(Optional.of(padrinho));
        when(profissionalPort.findById(2)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(buildRequest()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("2");

        verify(repositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("create: lança exceção quando vínculo ATIVO já existe entre padrinho e afilhado")
    void create_deveLancarExcecaoQuandoVinculoAtivoJaExiste() {
        var padrinho = buildProfissional(1L, "João Silva");
        var afilhado = buildProfissional(2L, "Maria Souza");

        when(profissionalPort.findById(1)).thenReturn(Optional.of(padrinho));
        when(profissionalPort.findById(2)).thenReturn(Optional.of(afilhado));
        when(repositoryPort.existsByPadrinhoIdAndAfilhadoIdAndStatus(1L, 2L, "ATIVO")).thenReturn(true);

        assertThatThrownBy(() -> service.create(buildRequest()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(Constantes.APADRINHAMENTO_REDE_VINCULO_JA_EXISTE);

        verify(repositoryPort, never()).save(any());
    }

    // ── getById ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getById: retorna response quando vínculo existe")
    void getById_deveRetornarResponse() {
        var padrinho = buildProfissional(1L, "João Silva");
        var afilhado = buildProfissional(2L, "Maria Souza");
        var entity   = buildEntity(10L, padrinho, afilhado);
        var response = buildResponse(10L);

        when(repositoryPort.findById(10L)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.getById(10L)).isEqualTo(response);
    }

    @Test
    @DisplayName("getById: lança exceção quando vínculo não existe")
    void getById_deveLancarExcecaoQuandoNaoEncontrado() {
        when(repositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ── listAll ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("listAll: retorna lista mapeada de todos os vínculos")
    void listAll_deveRetornarListaMapeada() {
        var padrinho = buildProfissional(1L, "João Silva");
        var afilhado = buildProfissional(2L, "Maria Souza");
        var entity   = buildEntity(10L, padrinho, afilhado);
        var response = buildResponse(10L);

        when(repositoryPort.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.listAll()).hasSize(1).containsExactly(response);
    }

    // ── list (paginado) ──────────────────────────────────────────────────────

    @Test
    @DisplayName("list: retorna página mapeada")
    void list_deveRetornarPaginaMapeada() {
        var pageable = PageRequest.of(0, 10);
        var padrinho = buildProfissional(1L, "João Silva");
        var afilhado = buildProfissional(2L, "Maria Souza");
        var entity   = buildEntity(10L, padrinho, afilhado);
        var response = buildResponse(10L);
        Page<ApadrinhamentoRede> page = new PageImpl<>(List.of(entity));

        when(repositoryPort.findAll(pageable)).thenReturn(page);
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.list(pageable).getContent()).containsExactly(response);
    }

    // ── listByPadrinho ───────────────────────────────────────────────────────

    @Test
    @DisplayName("listByPadrinho: retorna vínculos filtrados pelo padrinho")
    void listByPadrinho_deveRetornarVinculosDoPadrinho() {
        var padrinho = buildProfissional(1L, "João Silva");
        var afilhado = buildProfissional(2L, "Maria Souza");
        var entity   = buildEntity(10L, padrinho, afilhado);
        var response = buildResponse(10L);

        when(profissionalPort.findById(1)).thenReturn(Optional.of(padrinho));
        when(repositoryPort.findAllByPadrinhoId(1L)).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.listByPadrinho(1L)).hasSize(1).containsExactly(response);
    }

    @Test
    @DisplayName("listByPadrinho: lança exceção quando padrinho não existe")
    void listByPadrinho_deveLancarExcecaoQuandoPadrinhoNaoEncontrado() {
        when(profissionalPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.listByPadrinho(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ── listByAfilhado ───────────────────────────────────────────────────────

    @Test
    @DisplayName("listByAfilhado: retorna vínculos filtrados pelo afilhado")
    void listByAfilhado_deveRetornarVinculosDoAfilhado() {
        var padrinho = buildProfissional(1L, "João Silva");
        var afilhado = buildProfissional(2L, "Maria Souza");
        var entity   = buildEntity(10L, padrinho, afilhado);
        var response = buildResponse(10L);

        when(profissionalPort.findById(2)).thenReturn(Optional.of(afilhado));
        when(repositoryPort.findAllByAfilhadoId(2L)).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.listByAfilhado(2L)).hasSize(1).containsExactly(response);
    }

    @Test
    @DisplayName("listByAfilhado: lança exceção quando afilhado não existe")
    void listByAfilhado_deveLancarExcecaoQuandoAfilhadoNaoEncontrado() {
        when(profissionalPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.listByAfilhado(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ── update ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update: atualiza status/dataFim e retorna response")
    void update_deveAtualizarERetornarResponse() {
        var updateDTO = buildUpdateDTO();
        var padrinho  = buildProfissional(1L, "João Silva");
        var afilhado  = buildProfissional(2L, "Maria Souza");
        var entity    = buildEntity(10L, padrinho, afilhado);
        var response  = buildResponse(10L);

        when(repositoryPort.findById(10L)).thenReturn(Optional.of(entity));
        when(repositoryPort.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.update(10L, updateDTO)).isEqualTo(response);
        verify(mapper).updateFromDto(updateDTO, entity);
    }

    @Test
    @DisplayName("update: lança exceção quando vínculo não existe")
    void update_deveLancarExcecaoQuandoNaoEncontrado() {
        when(repositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, buildUpdateDTO()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ── delete ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete: remove vínculo quando existe")
    void delete_deveRemoverVinculo() {
        when(repositoryPort.existsById(10L)).thenReturn(true);

        service.delete(10L);

        verify(repositoryPort).deleteById(10L);
    }

    @Test
    @DisplayName("delete: lança exceção quando vínculo não existe")
    void delete_deveLancarExcecaoQuandoNaoEncontrado() {
        when(repositoryPort.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");

        verify(repositoryPort, never()).deleteById(any());
    }
}