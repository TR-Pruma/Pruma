package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.SubContratoRequestDTO;
import com.br.pruma.application.dto.response.SubContratoResponseDTO;
import com.br.pruma.application.dto.update.SubContratoUpdateDTO;
import com.br.pruma.application.mapper.SubContratoMapper;
import com.br.pruma.application.service.impl.SubContratoServiceImpl;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.domain.SubContrato;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import com.br.pruma.core.repository.SubContratoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubContratoServiceTest {

    @Mock SubContratoRepository repository;
    @Mock SubContratoMapper mapper;
    @Mock ClienteRepository clienteRepository;
    @Mock ProjetoRepository projetoRepository;
    @InjectMocks SubContratoServiceImpl service;

    SubContrato entity;
    SubContratoRequestDTO requestDTO;
    SubContratoUpdateDTO updateDTO;
    SubContratoResponseDTO responseDTO;
    Cliente cliente;
    Projeto projeto;

    @BeforeEach
    void setUp() {
        entity      = mock(SubContrato.class);
        requestDTO  = mock(SubContratoRequestDTO.class);
        updateDTO   = mock(SubContratoUpdateDTO.class);
        responseDTO = mock(SubContratoResponseDTO.class);
        cliente     = mock(Cliente.class);
        projeto     = mock(Projeto.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        when(requestDTO.clienteCpf()).thenReturn("123.456.789-00");
        when(requestDTO.projetoId()).thenReturn(1);
        when(mapper.toEntity(requestDTO)).thenReturn(entity);
        when(clienteRepository.findByCpf("123.456.789-00")).thenReturn(Optional.of(cliente));
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
        verify(entity).setCliente(cliente);
        verify(entity).setProjeto(projeto);
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("create: lanca EntityNotFoundException quando cliente nao existe")
    void create_clienteNaoEncontrado() {
        when(requestDTO.clienteCpf()).thenReturn("000.000.000-00");
        when(mapper.toEntity(requestDTO)).thenReturn(entity);
        when(clienteRepository.findByCpf("000.000.000-00")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(requestDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("000.000.000-00");
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
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listByProjeto: retorna lista filtrada por projeto")
    void listByProjeto() {
        when(repository.findByProjetoId(1)).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listByProjeto(1)).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(updateDTO.clienteCpf()).thenReturn(null);
        when(updateDTO.projetoId()).thenReturn(null);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, entity);
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("delete: deleta quando existe")
    void delete_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        service.delete(1);

        verify(repository).delete(entity);
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
        verify(repository, never()).delete(any(SubContrato.class));
    }
}