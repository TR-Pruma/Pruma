package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.EquipamentoProjetoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoProjetoResponseDTO;
import com.br.pruma.application.dto.update.EquipamentoProjetoUpdateDTO;
import com.br.pruma.application.mapper.EquipamentoProjetoMapper;
import com.br.pruma.application.service.impl.EquipamentoProjetoServiceImpl;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.EquipamentoProjeto;
import com.br.pruma.core.repository.EquipamentoProjetoRepository;
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
class EquipamentoProjetoServiceTest {

    @Mock EquipamentoProjetoRepository repository;
    @Mock EquipamentoProjetoMapper mapper;
    @InjectMocks EquipamentoProjetoServiceImpl service;

    EquipamentoProjeto entity;
    EquipamentoProjetoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(EquipamentoProjeto.class);
        responseDTO = mock(EquipamentoProjetoResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        var dto = mock(EquipamentoProjetoRequestDTO.class);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.create(dto)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.getById(1L)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca excecao quando nao existe")
    void getById_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listAll: retorna lista vazia quando nao ha registros")
    void listAll_vazia() {
        when(repository.findAll()).thenReturn(List.of());

        assertThat(service.listAll()).isEmpty();
    }

    @Test
    @DisplayName("list: retorna pagina mapeada")
    void list_paginado() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.list(pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(EquipamentoProjetoUpdateDTO.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.update(1L, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, entity);
    }

    @Test
    @DisplayName("update: lanca excecao quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(EquipamentoProjetoUpdateDTO.class);
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, updateDTO))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("delete: soft-delete quando existe")
    void delete_sucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);

        verify(entity).setAtivo(false);
        verify(repository).save(entity);
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("delete: lanca excecao quando nao existe")
    void delete_naoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99L))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}
