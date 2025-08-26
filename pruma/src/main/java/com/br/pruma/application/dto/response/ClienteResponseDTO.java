package com.br.pruma.application.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public record ClienteResponseDTO (
        Integer id,
        String cpf,
        String nome,
        String email,
        String telefone,
        Integer enderecoId,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,
        Long versao,
        Boolean ativo
) {}



