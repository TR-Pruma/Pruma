package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.MaterialRequestDTO;
import com.br.pruma.application.dto.response.MaterialResponseDTO;
import com.br.pruma.application.dto.update.MaterialUpdateDTO;
import com.br.pruma.application.mapper.MaterialMapper;
import com.br.pruma.application.service.impl.MaterialServiceImpl;
import com.br.pruma.core.domain.Material;
import com.br.pruma.core.repository.MaterialRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @Mock MaterialRepository repository;
    @Mock MaterialMapper mapper;
    @InjectMocks MaterialServiceImpl service;

    Material entity;
    MaterialResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(Material.class);
        responseDTO = mock(MaterialResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva quando descricao ainda nao existe")
    void create_sucesso() {
        var dto = mock(MaterialRequestDTO.class);
        when(dto.getDescricao()).thenReturn("Concreto");
        when(repository.findByDescricao("Concreto")).thenReturn(Optional.empty());
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.create(dto)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("create: lanca IllegalArgumentException quando descricao ja existe")
    void create_descricaoDuplicada() {
        var dto      = mock(MaterialRequestDTO.class);
        var existing = mock(Material.class);
        when(dto.getDescricao()).thenReturn("Concreto");
        when(repository.findByDescricao("Concreto")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Concreto");
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

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
    @DisplayName("listAll: retorna lista ordenada")
    void listAll() {
        when(repository.findAllByOrderByDescricaoAsc()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
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
    @DisplayName("update: atualiza quando descricao nao colidiu")
    void update_sucesso() {
        var updateDTO = mock(MaterialUpdateDTO.class);
        when(updateDTO.getDescricao()).thenReturn("Argamassa");
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(entity.getId()).thenReturn(1);
        when(repository.findByDescricao("Argamassa")).thenReturn(Optional.empty());
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, entity);
    }

    @Test
    @DisplayName("update: lanca IllegalArgumentException quando descricao pertence a outro material")
    void update_descricaoDeOutroMaterial() {
        var updateDTO = mock(MaterialUpdateDTO.class);
        var outro     = mock(Material.class);
        when(updateDTO.getDescricao()).thenReturn("Argamassa");
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(entity.getId()).thenReturn(1);
        when(outro.getId()).thenReturn(2);
        when(repository.findByDescricao("Argamassa")).thenReturn(Optional.of(outro));

        assertThatThrownBy(() -> service.update(1, updateDTO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(MaterialUpdateDTO.class);
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("replace: substitui quando existe")
    void replace_sucesso() {
        var dto = mock(MaterialRequestDTO.class);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.replace(1, dto)).isEqualTo(responseDTO);
        verify(entity).setId(1);
    }

    @Test
    @DisplayName("replace: lanca EntityNotFoundException quando nao existe")
    void replace_naoEncontrado() {
        var dto = mock(MaterialRequestDTO.class);
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("delete: deleta fisicamente quando existe")
    void delete_sucesso() {
        when(repository.existsById(1)).thenReturn(true);

        service.delete(1);

        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(repository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
