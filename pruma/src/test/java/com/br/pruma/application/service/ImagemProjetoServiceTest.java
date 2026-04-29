package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ImagemProjetoRequestDTO;
import com.br.pruma.application.dto.response.ImagemProjetoResponseDTO;
import com.br.pruma.application.dto.update.ImagemProjetoUpdateDTO;
import com.br.pruma.application.mapper.ImagemProjetoMapper;
import com.br.pruma.application.service.impl.ImagemProjetoServiceImpl;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.ImagemProjeto;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ImagemProjetoRepository;
import com.br.pruma.core.repository.ProjetoRepository;
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
class ImagemProjetoServiceTest {

    @Mock ImagemProjetoRepository repository;
    @Mock ProjetoRepository projetoRepository;
    @Mock ImagemProjetoMapper mapper;
    @InjectMocks ImagemProjetoServiceImpl service;

    ImagemProjeto entity;
    ImagemProjetoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(ImagemProjeto.class);
        responseDTO = mock(ImagemProjetoResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        var dto     = mock(ImagemProjetoRequestDTO.class);
        var projeto = mock(Projeto.class);
        when(dto.projetoId()).thenReturn(1);
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.create(dto)).isEqualTo(responseDTO);
        verify(entity).setProjeto(projeto);
    }

    @Test
    @DisplayName("create: lanca excecao quando projeto nao encontrado")
    void create_projetoNaoEncontrado() {
        var dto = mock(ImagemProjetoRequestDTO.class);
        when(dto.projetoId()).thenReturn(99);
        when(projetoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca excecao quando nao existe")
    void getById_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("list: retorna pagina mapeada")
    void list_paginado() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.list(pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listByProjeto: retorna lista filtrada por projeto")
    void listByProjeto() {
        when(repository.findAllByProjeto_Id(1)).thenReturn(List.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listByProjeto(1)).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(ImagemProjetoUpdateDTO.class);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, entity);
    }

    @Test
    @DisplayName("update: lanca excecao quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(ImagemProjetoUpdateDTO.class);
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(RecursoNaoEncontradoException.class);
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
    @DisplayName("delete: lanca excecao quando nao existe")
    void delete_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}
