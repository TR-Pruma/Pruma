package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.UsuarioRequestDTO;
import com.br.pruma.application.dto.response.UsuarioResponseDTO;
import com.br.pruma.application.dto.update.UsuarioUpdateDTO;
import com.br.pruma.application.mapper.UsuarioMapper;
import com.br.pruma.application.service.impl.UsuarioServiceImpl;
import com.br.pruma.core.domain.Usuario;
import com.br.pruma.core.enums.TipoUsuarioEnum;
import com.br.pruma.core.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioService — testes unitários")
class UsuarioServiceTest {

    @Mock private UsuarioRepository repository;
    @Mock private UsuarioMapper mapper;
    @InjectMocks private UsuarioServiceImpl service;

    // ── Usuario:           @SuperBuilder + @NoArgsConstructor(PROTECTED)
    //                       → Usuario.builder().build() — ativo herdado de AuditableEntity via @SuperBuilder
    // ── UsuarioRequestDTO: record → new UsuarioRequestDTO(...) — NUNCA mock()
    // ── UsuarioResponseDTO: record → new UsuarioResponseDTO(...) — NUNCA mock()
    // ── UsuarioUpdateDTO:  record → new UsuarioUpdateDTO(...) — NUNCA mock()

    private Usuario buildUsuario(Integer id) {
        return Usuario.builder()
                .id(id)
                .cpf("12345678901")
                .senha("senha123")
                .tipo(TipoUsuarioEnum.ADMIN)
                .ativo(true)          // Boolean herdado via @SuperBuilder
                .build();
    }

    private UsuarioRequestDTO buildRequest() {
        return new UsuarioRequestDTO("12345678901", "senha123", TipoUsuarioEnum.ADMIN);
    }

    private UsuarioResponseDTO buildResponse(Integer id) {
        return new UsuarioResponseDTO(id, "12345678901", TipoUsuarioEnum.ADMIN, true,
                LocalDateTime.now(), LocalDateTime.now());
    }

    // ── create ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create: salva e retorna UsuarioResponseDTO")
    void create_sucesso() {
        var req      = buildRequest();
        var entity   = buildUsuario(null);
        var saved    = buildUsuario(1);
        var response = buildResponse(1);

        when(mapper.toEntity(req)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        assertThat(service.create(req)).isEqualTo(response);
        verify(repository).save(entity);
    }

    // ── getById ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getById: retorna DTO quando usuário existe")
    void getById_encontrado() {
        var entity   = buildUsuario(1);
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
    @DisplayName("listAll: retorna lista de DTOs")
    void listAll_comRegistros() {
        var entity   = buildUsuario(1);
        var response = buildResponse(1);

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

    // ── update ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update: atualiza campos e retorna DTO")
    void update_sucesso() {
        // record → construtor canônico, todos os campos nullable são opcionais com null
        var upd      = new UsuarioUpdateDTO("novaSenha123", TipoUsuarioEnum.ADMIN, null);
        var entity   = buildUsuario(1);
        var response = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.update(1, upd)).isEqualTo(response);
        verify(mapper).updateFromDto(upd, entity);
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("update: não encontrado lança EntityNotFoundException")
    void update_naoEncontrado() {
        var upd = new UsuarioUpdateDTO(null, null, null);
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, upd))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ── delete ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete: chama deleteById quando usuário existe")
    void delete_sucesso() {
        when(repository.existsById(1)).thenReturn(true);

        assertThatCode(() -> service.delete(1)).doesNotThrowAnyException();
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("delete: não encontrado lança EntityNotFoundException")
    void delete_naoEncontrado() {
        when(repository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");

        verify(repository, never()).deleteById(any());
    }
}