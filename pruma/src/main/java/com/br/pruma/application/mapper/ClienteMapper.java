package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Endereco;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    private final PasswordEncoder passwordEncoder;

    public ClienteMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Cliente toEntity(ClienteRequestDTO dto, Endereco endereco) {
        return Cliente.builder()
                .cpf(dto.getCpf())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .endereco(endereco)
                .senha(passwordEncoder.encode(dto.getSenha()))
                .ativo(true)
                .build();
    }

    public ClienteResponseDTO toDTO(Cliente entity) {
        String enderecoCompleto = "";
        if (entity.getEndereco() != null) {
            Endereco endereco = entity.getEndereco();
            enderecoCompleto = String.format("%s, %s%s - %s, %s/%s, CEP: %s",
                    endereco.getLogradouro(),
                    endereco.getNumero(),
                    endereco.getComplemento() != null && !endereco.getComplemento().isEmpty() ?
                        ", " + endereco.getComplemento() : "",
                    endereco.getBairro(),
                    endereco.getCidade(),
                    endereco.getUf(),
                    endereco.getCep());
        }

        return ClienteResponseDTO.builder()
                .id(entity.getId())
                .cpf(entity.getCpf())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .telefone(entity.getTelefone())
                .enderecoCompleto(enderecoCompleto)
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .versao(entity.getVersao())
                .ativo(entity.getAtivo())
                .build();
    }

    public void updateEntity(Cliente entity, ClienteRequestDTO dto, Endereco endereco) {
        entity.setCpf(dto.getCpf());
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setEndereco(endereco);
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            entity.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
    }
}
