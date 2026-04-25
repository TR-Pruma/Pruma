package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.AnexoRequestDTO;
import com.br.pruma.application.dto.response.AnexoResponseDTO;
import com.br.pruma.application.mapper.AnexoMapper;
import com.br.pruma.application.service.impl.AnexoServiceImpl;
import com.br.pruma.core.domain.Anexo;
import com.br.pruma.core.repository.AnexoRepository;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AnexoService — testes unitários")
class AnexoServiceTest {

    @Mock private AnexoRepository repository;
    @Mock private AnexoMapper mapper;
    @InjectMocks private AnexoServiceImpl service;


    private Anexo buildAnexo(Integer id) {
        return Anexo.builder()
                .id(id)
                .tipoAnexo("Documento")
                .caminho("/uploads/doc.pdf")
                .nomeArquivo("doc.pdf")
                .contentType("application/pdf")
                .tamanhoBytes(1024L)
                .ativo(true)
                .build();
    }

    private AnexoRequestDTO buildRequest() {
        return AnexoRequestDTO.builder()
                .projetoId(10)
                .tipoAnexo("Documento")
                .caminhoArquivo("/uploads/doc.pdf")
                .build();
    }

    private AnexoResponseDTO buildResponse() {
        return new AnexoResponseDTO(1, 10, "Documento", "/uploads/doc.pdf");
    }

    // ── create ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        var req      = buildRequest();
        var entity   = buildAnexo(null);
        var saved    = buildAnexo(1);
        var response = buildResponse();

        when(mapper.toEntity(req)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        assertThat(service.create(req)).isEqualTo(response);
        verify(repository).save(entity);
    }

    // ── getById ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        var entity   = buildAnexo(1);
        var response = buildResponse();

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.getById(1)).isEqualTo(response);
    }

    @Test
    @DisplayName("getById: não encontrado lança EntityNotFoundException")
    void getById_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class);
    }

    // ── listAll ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("listAll: retorna lista de DTOs")
    void listAll_comRegistros() {
        var entity   = buildAnexo(1);
        var response = buildResponse();

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.listAll()).hasSize(1).containsExactly(response);
    }

    @Test
    @DisplayName("listAll: retorna lista vazia")
    void listAll_vazio() {
        when(repository.findAll()).thenReturn(List.of());
        assertThat(service.listAll()).isEmpty();
    }

    // ── list (paginado) ──────────────────────────────────────────────────────

    @Test
    @DisplayName("list: retorna página de DTOs")
    void list_paginado() {
        var pageable = PageRequest.of(0, 10);
        var entity   = buildAnexo(1);
        var response = buildResponse();
        Page<Anexo> page = new PageImpl<>(List.of(entity));

        when(repository.findAll(pageable)).thenReturn(page);
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.list(pageable).getContent()).containsExactly(response);
    }

    // ── update ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update: atualiza via updateFromRequest e retorna DTO")
    void update_sucesso() {
        var req      = buildRequest();
        var entity   = buildAnexo(1);
        var response = buildResponse();

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.update(1, req)).isEqualTo(response);
        verify(mapper).updateFromRequest(req, entity);
    }

    @Test
    @DisplayName("update: não encontrado lança EntityNotFoundException")
    void update_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, buildRequest()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    // ── delete (soft) ────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete: soft delete — setAtivo(false) e salva")
    void delete_sucesso() {
        var entity = buildAnexo(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));

        assertThatCode(() -> service.delete(1)).doesNotThrowAnyException();

        assertThat(entity.getAtivo()).isFalse();
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("delete: não encontrado lança EntityNotFoundException")
    void delete_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class);

        verify(repository, never()).save(any());
    }
}