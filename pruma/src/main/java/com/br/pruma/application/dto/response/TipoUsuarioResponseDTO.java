package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "TipoUsuarioResponseDTO", description = "Representação de tipo de usuário retornada pela API")
public class TipoUsuarioResponseDTO {

    @Schema(description = "Identificador único", example = "1", required = true)
    private Integer id;

    @Schema(description = "Descrição do tipo de usuário", example = "Administrador", required = true)
    private String descricao;

    @Schema(description = "Timestamp de criação")
    private LocalDateTime dataCriacao;

    @Schema(description = "Timestamp de atualização")
    private LocalDateTime dataAtualizacao;

    @Schema(description = "Versão para controle otimista")
    private Long versao;

    @Schema(description = "Indica se o tipo de usuário está ativo")
    private Boolean ativo;
}
