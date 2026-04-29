package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ProjetoCategoriaRequestDTO;
import com.br.pruma.application.dto.response.ProjetoCategoriaResponseDTO;
import com.br.pruma.application.dto.update.ProjetoCategoriaUpdateDTO;
import com.br.pruma.application.mapper.ProjetoCategoriaMapper;
import com.br.pruma.application.service.impl.ProjetoCategoriaServiceImpl;
import com.br.pruma.core.domain.ProjetoCategoria;
import com.br.pruma.core.repository.ProjetoCategoriaRepository;
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
class ProjetoCategoriaServiceTest {

    @Mock ProjetoCategoriaRepository repository;
    @Mock ProjetoCategoriaMapper mapper;
    @InjectMocks ProjetoCategoriaServiceImpl service;

    ProjetoCategoria entity;
    ProjetoCategoriaResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(ProjetoCategoria.class);
        responseDTO = mock(ProjetoCategoriaResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        var dto = mock(ProjetoCategoriaRequestDTO.class);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.create(dto)).isEqualTo(responseDTO);
    }

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

    @Test
    @DisplayName("listAll: retorna lista mapeada via toDTOList")
    void listAll() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTOList(List.of(entity))).thenReturn(List.of(responseDTO));

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
    @DisplayName("listByProjeto: retorna lista vazia (nao implementado)")
    void listByProjeto_vazia() {
        assertThat(service.listByProjeto(1)).isEmpty();
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(ProjetoCategoriaUpdateDTO.class);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, entity);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(ProjetoCategoriaUpdateDTO.class);
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

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
