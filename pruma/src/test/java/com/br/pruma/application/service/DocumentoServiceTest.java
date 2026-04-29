package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.DocumentoRequestDTO;
import com.br.pruma.application.dto.response.DocumentoResponseDTO;
import com.br.pruma.application.mapper.DocumentoMapper;
import com.br.pruma.application.service.impl.DocumentoServiceImpl;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Documento;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.domain.TipoDocumento;
import com.br.pruma.core.repository.DocumentoRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import com.br.pruma.core.repository.TipoDocumentoRepository;
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
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentoServiceTest {

    @Mock DocumentoRepository repository;
    @Mock ProjetoRepository projetoRepository;
    @Mock TipoDocumentoRepository tipoDocumentoRepository;
    @Mock DocumentoMapper mapper;
    @InjectMocks DocumentoServiceImpl service;

    Documento entity;
    DocumentoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(Documento.class);
        responseDTO = mock(DocumentoResponseDTO.class);
    }

    @Test
    @DisplayName("upload: salva e retorna DTO")
    void upload_sucesso() {
        var dto          = mock(DocumentoRequestDTO.class);
        var projeto      = mock(Projeto.class);
        var tipoDoc      = mock(TipoDocumento.class);
        var multipart    = new MockMultipartFile("file", "doc.pdf", "application/pdf", new byte[0]);
        when(dto.getProjetoId()).thenReturn(1);
        when(dto.getTipoDocumentoId()).thenReturn(2);
        when(dto.getArquivo()).thenReturn(multipart);
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(tipoDocumentoRepository.findById(2)).thenReturn(Optional.of(tipoDoc));
        when(mapper.gerarCaminhoArquivo(anyString())).thenReturn("/tmp/doc.pdf");
        when(mapper.toEntity(eq(dto), eq(projeto), eq(tipoDoc), eq("/tmp/doc.pdf"))).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.upload(dto)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("upload: lanca excecao quando projeto nao encontrado")
    void upload_projetoNaoEncontrado() {
        var dto = mock(DocumentoRequestDTO.class);
        when(dto.getProjetoId()).thenReturn(99);
        when(projetoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.upload(dto))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("upload: lanca excecao quando tipoDocumento nao encontrado")
    void upload_tipoDocumentoNaoEncontrado() {
        var dto    = mock(DocumentoRequestDTO.class);
        var projeto = mock(Projeto.class);
        when(dto.getProjetoId()).thenReturn(1);
        when(dto.getTipoDocumentoId()).thenReturn(99);
        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(tipoDocumentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.upload(dto))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("buscarPorId: retorna DTO quando existe")
    void buscarPorId_encontrado() {
        when(repository.findByIdAndAtivoTrue(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.buscarPorId(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("buscarPorId: lanca excecao quando nao existe")
    void buscarPorId_naoEncontrado() {
        when(repository.findByIdAndAtivoTrue(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }

    @Test
    @DisplayName("listarPorProjeto: retorna pagina mapeada")
    void listarPorProjeto() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findByProjetoIdAndAtivoTrueOrderByDataUploadDesc(1, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listarPorProjeto(1, pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listarPorTipoDocumento: retorna lista mapeada")
    void listarPorTipoDocumento() {
        when(repository.findByTipoDocumentoIdAndAtivoTrueOrderByDataUploadDesc(2))
                .thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listarPorTipoDocumento(2)).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listarPorProjetoETipo: retorna lista mapeada")
    void listarPorProjetoETipo() {
        when(repository.findByProjetoIdAndTipoDocumentoIdAndAtivoTrue(1, 2))
                .thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listarPorProjetoETipo(1, 2)).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("deletar: soft-delete quando existe")
    void deletar_sucesso() {
        when(repository.findByIdAndAtivoTrue(1)).thenReturn(Optional.of(entity));

        service.deletar(1);

        verify(entity).setAtivo(false);
        verify(repository).save(entity);
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("deletar: lanca excecao quando nao existe")
    void deletar_naoEncontrado() {
        when(repository.findByIdAndAtivoTrue(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}
