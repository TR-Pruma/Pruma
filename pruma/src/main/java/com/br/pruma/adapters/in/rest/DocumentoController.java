package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.DocumentoRequestDTO;
import com.br.pruma.application.dto.response.DocumentoResponseDTO;
import com.br.pruma.application.service.DocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@RequiredArgsConstructor
@Tag(name = "Documentos", description = "Endpoints para gerenciamento de documentos")
public class DocumentoController {

    private final DocumentoService documentoService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Fazer upload de documento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Documento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Projeto ou tipo de documento não encontrado")
    })
    public ResponseEntity<DocumentoResponseDTO> upload(
            @Parameter(description = "Dados do documento", required = true)
            @Valid @ModelAttribute DocumentoRequestDTO request) {
        return ResponseEntity.ok(documentoService.upload(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar documento por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Documento encontrado"),
        @ApiResponse(responseCode = "404", description = "Documento não encontrado")
    })
    public ResponseEntity<DocumentoResponseDTO> buscarPorId(
            @Parameter(description = "ID do documento", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(documentoService.buscarPorId(id));
    }

    @GetMapping("/projeto/{projetoId}")
    @Operation(summary = "Listar documentos por projeto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de documentos retornada"),
        @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity<Page<DocumentoResponseDTO>> listarPorProjeto(
            @Parameter(description = "ID do projeto", required = true)
            @PathVariable Integer projetoId,
            @Parameter(description = "Número da página (0..N)", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Quantidade de elementos por página", example = "10")
            @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "Critério de ordenação (dataUpload,desc)")
            @RequestParam(defaultValue = "dataUpload,desc") String sort) {

        PageRequest pageable = PageRequest.of(
            page,
            size,
            Sort.by(sort.split(",")[0]).descending()
        );

        return ResponseEntity.ok(documentoService.listarPorProjeto(projetoId, pageable));
    }

    @GetMapping("/tipo/{tipoDocumentoId}")
    @Operation(summary = "Listar documentos por tipo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de documentos retornada"),
        @ApiResponse(responseCode = "404", description = "Tipo de documento não encontrado")
    })
    public ResponseEntity<List<DocumentoResponseDTO>> listarPorTipoDocumento(
            @Parameter(description = "ID do tipo de documento", required = true)
            @PathVariable Integer tipoDocumentoId) {
        return ResponseEntity.ok(documentoService.listarPorTipoDocumento(tipoDocumentoId));
    }

    @GetMapping("/projeto/{projetoId}/tipo/{tipoDocumentoId}")
    @Operation(summary = "Listar documentos por projeto e tipo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de documentos retornada"),
        @ApiResponse(responseCode = "404", description = "Projeto ou tipo de documento não encontrado")
    })
    public ResponseEntity<List<DocumentoResponseDTO>> listarPorProjetoETipo(
            @Parameter(description = "ID do projeto", required = true)
            @PathVariable Integer projetoId,
            @Parameter(description = "ID do tipo de documento", required = true)
            @PathVariable Integer tipoDocumentoId) {
        return ResponseEntity.ok(documentoService.listarPorProjetoETipo(projetoId, tipoDocumentoId));
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "Download de documento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arquivo retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento não encontrado")
    })
    public ResponseEntity<Resource> download(
            @Parameter(description = "ID do documento", required = true)
            @PathVariable Integer id) {
        DocumentoResponseDTO documento = documentoService.buscarPorId(id);
        Resource arquivo = documentoService.download(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(documento.getTipoArquivo()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documento.getNomeArquivo() + "\"")
                .body(arquivo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir documento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Documento excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento não encontrado")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @Parameter(description = "ID do documento", required = true)
            @PathVariable Integer id) {
        documentoService.deletar(id);
    }
}
