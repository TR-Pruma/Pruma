package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.DocumentoRequestDTO;
import com.br.pruma.application.dto.response.DocumentoResponseDTO;
import com.br.pruma.core.domain.Documento;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.domain.TipoDocumento;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@Component
public class DocumentoMapper {
    public Documento toEntity(DocumentoRequestDTO dto, Projeto projeto, TipoDocumento tipoDocumento, String caminhoArquivo) {
        MultipartFile arquivo = dto.getArquivo();

        return Documento.builder()
                .projeto(projeto)
                .tipoDocumento(tipoDocumento)
                .nomeArquivo(arquivo.getOriginalFilename())
                .caminhoArquivo(caminhoArquivo)
                .tipoArquivo(arquivo.getContentType())
                .tamanhoArquivo(arquivo.getSize())
                .ativo(true)
                .build();
    }

    public DocumentoResponseDTO toResponse(Documento entity) {
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/documentos/{id}/download")
                .buildAndExpand(entity.getId())
                .toUriString();

        return DocumentoResponseDTO.builder()
                .id(entity.getId())
                .projetoId(entity.getProjeto().getId())
                .projetoNome(entity.getProjeto().getNome())
                .tipoDocumentoId(entity.getTipoDocumento().getId())
                .tipoDocumentoNome(entity.getTipoDocumento().getNome())
                .nomeArquivo(entity.getNomeArquivo())
                .tipoArquivo(entity.getTipoArquivo())
                .tamanhoArquivo(entity.getTamanhoArquivo())
                .urlDownload(downloadUrl)
                .dataUpload(entity.getDataUpload())
                .dataAtualizacao(entity.getDataAtualizacao())
                .versao(entity.getVersao())
                .ativo(entity.getAtivo())
                .build();
    }

    public String gerarCaminhoArquivo(String nomeOriginal) {
        String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
        return UUID.randomUUID().toString() + extensao;
    }
}

