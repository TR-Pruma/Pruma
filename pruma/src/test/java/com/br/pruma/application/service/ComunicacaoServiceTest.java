package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ComunicacaoRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoResponseDTO;
import com.br.pruma.application.mapper.ComunicacaoMapper;
import com.br.pruma.application.service.impl.ComunicacaoServiceImpl;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Comunicacao;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.ComunicacaoRepository;
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
class ComunicacaoServiceTest {

    @Mock ComunicacaoRepository comunicacaoRepository;
    @Mock ProjetoRepository projetoRepository;
    @Mock ClienteRepository clienteRepository;
    @Mock ComunicacaoMapper comunicacaoMapper;
    @InjectMocks ComunicacaoServiceImpl service;

    Comunicacao entity;
    ComunicacaoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(Comunicacao.class);
        responseDTO = mock(ComunicacaoResponseDTO.class);
    }

    @Test
    @DisplayName("criar: salva e retorna DTO")
    void criar_sucesso() {
        var dto     = mock(ComunicacaoRequestDTO.class);
        var projeto = mock(Projeto.class);
        var cliente = mock(Cliente.class);
        when(dto.getProjetoId()).thenReturn(1);
        when(dto.getClienteId()).thenReturn(2);
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(clienteRepository.findById(2)).thenReturn(Optional.of(cliente));
        when(comunicacaoMapper.toEntity(dto, projeto, cliente)).thenReturn(entity);
        when(comunicacaoRepository.save(entity)).thenReturn(entity);
        when(comunicacaoMapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.criar(dto)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("criar: lanca excecao quando projeto nao encontrado")
    void criar_projetoNaoEncontrado() {
        var dto = mock(ComunicacaoRequestDTO.class);
        when(dto.getProjetoId()).thenReturn(99);
        when(projetoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.criar(dto))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("criar: lanca excecao quando cliente nao encontrado")
    void criar_clienteNaoEncontrado() {
        var dto     = mock(ComunicacaoRequestDTO.class);
        var projeto = mock(Projeto.class);
        when(dto.getProjetoId()).thenReturn(1);
        when(dto.getClienteId()).thenReturn(99);
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.criar(dto))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("buscarPorId: retorna DTO quando existe")
    void buscarPorId_encontrado() {
        when(comunicacaoRepository.findByIdAndAtivoTrue(1)).thenReturn(Optional.of(entity));
        when(comunicacaoMapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.buscarPorId(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorId: lanca excecao quando nao existe")
    void buscarPorId_naoEncontrado() {
        when(comunicacaoRepository.findByIdAndAtivoTrue(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("listarPorProjeto: retorna pagina mapeada")
    void listarPorProjeto() {
        Pageable pageable = PageRequest.of(0, 10);
        when(comunicacaoRepository.findByProjetoIdAndAtivoTrueOrderByCreatedAtDesc(1, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(comunicacaoMapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listarPorProjeto(1, pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listarPorCliente: retorna lista mapeada")
    void listarPorCliente() {
        when(comunicacaoRepository.findByClienteIdAndAtivoTrueOrderByCreatedAtDesc(2))
                .thenReturn(List.of(entity));
        when(comunicacaoMapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listarPorCliente(2)).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listarPorProjetoECliente: retorna pagina mapeada")
    void listarPorProjetoECliente() {
        Pageable pageable = PageRequest.of(0, 10);
        when(comunicacaoRepository.findByProjetoIdAndClienteIdAndAtivoTrueOrderByCreatedAtDesc(1, 2, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(comunicacaoMapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listarPorProjetoECliente(1, 2, pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("atualizar: atualiza quando existe")
    void atualizar_sucesso() {
        var dto = mock(ComunicacaoRequestDTO.class);
        when(comunicacaoRepository.findByIdAndAtivoTrue(1)).thenReturn(Optional.of(entity));
        when(comunicacaoRepository.save(entity)).thenReturn(entity);
        when(comunicacaoMapper.toDTO(entity)).thenReturn(responseDTO);

        assertThat(service.atualizar(1, dto)).isEqualTo(responseDTO);
        verify(comunicacaoMapper).updateEntity(entity, dto);
    }

    @Test
    @DisplayName("atualizar: lanca excecao quando nao existe")
    void atualizar_naoEncontrado() {
        var dto = mock(ComunicacaoRequestDTO.class);
        when(comunicacaoRepository.findByIdAndAtivoTrue(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99, dto))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("deletar: soft-delete quando existe")
    void deletar_sucesso() {
        when(comunicacaoRepository.findById(1)).thenReturn(Optional.of(entity));

        service.deletar(1);

        verify(entity).setAtivo(false);
        verify(comunicacaoRepository).save(entity);
        verify(comunicacaoRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("deletar: lanca excecao quando nao existe")
    void deletar_naoEncontrado() {
        when(comunicacaoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}
