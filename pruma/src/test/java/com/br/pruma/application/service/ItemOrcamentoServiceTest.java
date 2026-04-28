package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ItemOrcamentoRequestDTO;
import com.br.pruma.application.dto.response.ItemOrcamentoResponseDTO;
import com.br.pruma.application.dto.update.ItemOrcamentoUpdateDTO;
import com.br.pruma.application.mapper.ItemOrcamentoMapper;
import com.br.pruma.application.service.impl.ItemOrcamentoServiceImpl;
import com.br.pruma.config.Constantes;
import com.br.pruma.core.domain.ItemOrcamento;
import com.br.pruma.core.repository.port.ItemOrcamentoRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ItemOrcamentoService — testes unitários")
class ItemOrcamentoServiceTest {

    @Mock private ItemOrcamentoRepositoryPort itemOrcamentoRepositoryPort;
    @Mock private ItemOrcamentoMapper itemOrcamentoMapper;
    @InjectMocks private ItemOrcamentoServiceImpl service;

    private ItemOrcamento buildItem(Integer id) {
        return ItemOrcamento.builder()
                .id(id)
                .descricao("Item Teste")
                .quantidade(2)
                .valorUnitario(BigDecimal.valueOf(100.00))
                .build();
    }

    private ItemOrcamentoRequestDTO buildRequest() {
        return ItemOrcamentoRequestDTO.builder()
                .orcamentoId(5)
                .descricao("Item Teste")
                .quantidade(2)
                .valorUnitario(BigDecimal.valueOf(100.00))
                .build();
    }

    private ItemOrcamentoResponseDTO buildResponse() {
        return ItemOrcamentoResponseDTO.builder()
                .id(1)
                .orcamentoId(5)
                .descricao("Item Teste")
                .quantidade(2)
                .valorUnitario(BigDecimal.valueOf(100.00))
                .build();
    }

    // ── create ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create: salva via port e retorna DTO")
    void create_sucesso() {
        var req      = buildRequest();
        var entity   = buildItem(null);
        var saved    = buildItem(1);
        var response = buildResponse();

        when(itemOrcamentoMapper.toEntity(req)).thenReturn(entity);
        when(itemOrcamentoRepositoryPort.save(entity)).thenReturn(saved);
        when(itemOrcamentoMapper.toResponse(saved)).thenReturn(response);

        assertThat(service.create(req)).isEqualTo(response);
        verify(itemOrcamentoRepositoryPort).save(entity);
    }

    // ── getById ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getById: retorna DTO quando item existe")
    void getById_encontrado() {
        var entity   = buildItem(1);
        var response = buildResponse();

        when(itemOrcamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(itemOrcamentoMapper.toResponse(entity)).thenReturn(response);

        assertThat(service.getById(1)).isEqualTo(response);
    }

    @Test
    @DisplayName("getById: não encontrado lança EntityNotFoundException com constante")
    void getById_naoEncontrado() {
        when(itemOrcamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(Constantes.ITEM_ORCAMENTO_NAO_ENCONTRADO);
    }

    // ── listAll ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("listAll: retorna lista de DTOs")
    void listAll_comRegistros() {
        var entity   = buildItem(1);
        var response = buildResponse();

        when(itemOrcamentoRepositoryPort.findAll()).thenReturn(List.of(entity));
        when(itemOrcamentoMapper.toResponse(entity)).thenReturn(response);

        assertThat(service.listAll()).hasSize(1).containsExactly(response);
    }

    @Test
    @DisplayName("listAll: retorna lista vazia")
    void listAll_vazio() {
        when(itemOrcamentoRepositoryPort.findAll()).thenReturn(List.of());
        assertThat(service.listAll()).isEmpty();
    }

    // ── listByOrcamento ──────────────────────────────────────────────────────

    @Test
    @DisplayName("listByOrcamento: retorna itens do orçamento informado")
    void listByOrcamento_comRegistros() {
        var entity   = buildItem(1);
        var response = buildResponse();

        when(itemOrcamentoRepositoryPort.findByOrcamentoId(5)).thenReturn(List.of(entity));
        when(itemOrcamentoMapper.toResponse(entity)).thenReturn(response);

        assertThat(service.listByOrcamento(5)).hasSize(1).containsExactly(response);
        verify(itemOrcamentoRepositoryPort).findByOrcamentoId(5);
    }

    @Test
    @DisplayName("listByOrcamento: retorna lista vazia quando não há itens")
    void listByOrcamento_vazio() {
        when(itemOrcamentoRepositoryPort.findByOrcamentoId(5)).thenReturn(List.of());
        assertThat(service.listByOrcamento(5)).isEmpty();
    }

    // ── update ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update: atualiza campos e retorna DTO")
    void update_sucesso() {
        var upd = ItemOrcamentoUpdateDTO.builder()
                .descricao("Nova Descrição")
                .quantidade(5)
                .build();
        var entity   = buildItem(1);
        var response = buildResponse();

        when(itemOrcamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));
        when(itemOrcamentoRepositoryPort.save(entity)).thenReturn(entity);
        when(itemOrcamentoMapper.toResponse(entity)).thenReturn(response);

        assertThat(service.update(1, upd)).isEqualTo(response);
        verify(itemOrcamentoMapper).updateFromDto(upd, entity);
    }

    @Test
    @DisplayName("update: não encontrado lança EntityNotFoundException")
    void update_naoEncontrado() {
        when(itemOrcamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, ItemOrcamentoUpdateDTO.builder().build()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    // ── replace ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("replace: substitui item completo mantendo o mesmo ID")
    void replace_sucesso() {
        var req      = buildRequest();
        var novo     = buildItem(null);
        var saved    = buildItem(1);
        var response = buildResponse();

        when(itemOrcamentoRepositoryPort.findById(1)).thenReturn(Optional.of(buildItem(1)));
        when(itemOrcamentoMapper.toEntity(req)).thenReturn(novo);
        when(itemOrcamentoRepositoryPort.save(any())).thenReturn(saved);
        when(itemOrcamentoMapper.toResponse(saved)).thenReturn(response);

        assertThat(service.replace(1, req)).isEqualTo(response);
        verify(itemOrcamentoRepositoryPort).save(argThat(i -> i.getId().equals(1)));
    }

    @Test
    @DisplayName("replace: não encontrado lança EntityNotFoundException")
    void replace_naoEncontrado() {
        when(itemOrcamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, buildRequest()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    // ── delete (hard) ────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete: chama port.delete(entity) — hard delete por entidade")
    void delete_sucesso() {
        var entity = buildItem(1);
        when(itemOrcamentoRepositoryPort.findById(1)).thenReturn(Optional.of(entity));

        assertThatCode(() -> service.delete(1)).doesNotThrowAnyException();

        // port expõe apenas delete(entity) — deleteById não existe no contrato
        verify(itemOrcamentoRepositoryPort).delete(entity);
    }

    @Test
    @DisplayName("delete: não encontrado lança EntityNotFoundException")
    void delete_naoEncontrado() {
        when(itemOrcamentoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class);

        verify(itemOrcamentoRepositoryPort, never()).delete(any());
    }
}