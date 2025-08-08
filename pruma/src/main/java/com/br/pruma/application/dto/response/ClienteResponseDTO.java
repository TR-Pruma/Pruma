package com.br.pruma.application.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {
    private Integer id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private String enderecoCompleto;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Long versao;
    private Boolean ativo;
}


