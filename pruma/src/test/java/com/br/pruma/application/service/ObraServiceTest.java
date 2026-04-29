package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ObraRequestDTO;
import com.br.pruma.application.dto.response.ObraResponseDTO;
import com.br.pruma.application.dto.update.ObraUpdateDTO;
import com.br.pruma.application.mapper.ObraMapper;
import com.br.pruma.application.service.impl.ObraServiceImpl;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ObraRepository;
import com.br.pruma.core.repository.ProjetoRepository;
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
class ObraServiceTest {

    @Mock ObraRepository obraRepository;
    @Mock ProjetoRepository projetoRepository;
    @Mock ObraMapper mapper;
    @InjectMocks ObraServiceImpl service;

    Obra entity;
    ObraResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(Obra.class);
        responseDTO = mock(ObraResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        var dto     = mock(ObraRequestDTO.class);
        var projeto = mock(Projeto.class);
        when(dto.getProjetoId()).thenReturn(1);
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(obraRepository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.create(dto)).isEqualTo(responseDTO);
        verify(entity).setProjeto(projeto);
    }

    @Test
    @DisplayName("create: lanca EntityNotFoundException quando projeto nao existe")
    void create_projetoNaoEncontrado() {
        var dto = mock(ObraRequestDTO.class);
        when(dto.getProjetoId()).thenReturn(99);
        when(projetoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(obraRepository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca EntityNotFoundException quando nao existe")
    void getById_naoEncontrado() {
        when(obraRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(obraRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("list: retorna pagina mapeada")
    void list_paginado() {
        Pageable pageable = PageRequest.of(0, 10);
        when(obraRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.list(pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listByProjeto: retorna lista filtrada")
    void listByProjeto() {
        when(obraRepository.findAllByProjeto_Id(2)).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listByProjeto(2)).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza sem trocar projeto quando projetoId null")
    void update_semTrocarProjeto() {
        var updateDTO = mock(ObraUpdateDTO.class);
        when(updateDTO.getProjetoId()).thenReturn(null);
        when(obraRepository.findById(1)).thenReturn(Optional.of(entity));
        when(obraRepository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, entity);
        verifyNoInteractions(projetoRepository);
    }

    @Test
    @DisplayName("update: troca projeto quando projetoId fornecido")
    void update_comNovoProjeto() {
        var updateDTO = mock(ObraUpdateDTO.class);
        var projeto   = mock(Projeto.class);
        when(updateDTO.getProjetoId()).thenReturn(5);
        when(obraRepository.findById(1)).thenReturn(Optional.of(entity));
        when(projetoRepository.findById(5)).thenReturn(Optional.of(projeto));
        when(obraRepository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(entity).setProjeto(projeto);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando obra nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(ObraUpdateDTO.class);
        when(obraRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("replace: substitui quando obra e projeto existem")
    void replace_sucesso() {
        var dto     = mock(ObraRequestDTO.class);
        var projeto = mock(Projeto.class);
        when(dto.getProjetoId()).thenReturn(1);
        when(obraRepository.findById(1)).thenReturn(Optional.of(entity));
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(obraRepository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.replace(1, dto)).isEqualTo(responseDTO);
        verify(entity).setId(1);
        verify(entity).setProjeto(projeto);
    }

    @Test
    @DisplayName("replace: lanca EntityNotFoundException quando obra nao existe")
    void replace_obraNaoEncontrada() {
        var dto = mock(ObraRequestDTO.class);
        when(obraRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("searchByDescricao: retorna pagina com resultados encontrados")
    void searchByDescricao() {
        Pageable pageable = PageRequest.of(0, 10);
        when(obraRepository.findAllByDescricaoContainingIgnoreCase("casa")).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        var page = service.searchByDescricao("casa", pageable);

        assertThat(page.getContent()).containsExactly(responseDTO);
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("delete: soft-delete quando existe")
    void delete_sucesso() {
        when(obraRepository.findById(1)).thenReturn(Optional.of(entity));

        service.delete(1);

        verify(entity).setAtivo(false);
        verify(obraRepository).save(entity);
        verify(obraRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(obraRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
