package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Endereco;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {EnderecoMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
@DecoratedWith(ClienteMapperDecorator.class)
public interface ClienteMapper{
    // Criação de entidade a partir do DTO + Endereco já carregado (via service/repository)
    @Mapping(target = "endereco", source = "endereco")
    @Mapping(target = "senha", ignore = true) // será tratada no decorator (PasswordEncoder)
    @Mapping(target = "ativo", constant = "true") // padrão de negócio
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "versao", ignore = true)
    Cliente toEntity(ClienteRequestDTO dto, Endereco endereco);

    // Conversão para response, compondo o endereço completo
    @Mapping(target = "enderecoCompleto", source = "endereco", qualifiedByName = "composeEnderecoCompleto")
    ClienteResponseDTO toResponseDTO(Cliente cliente);

    // Conversão em lote (útil para listagens/paginação)
    List<ClienteResponseDTO> toResponseDTO(List<Cliente> clientes);

    // Atualização da entidade, ignorando nulos no DTO (parcial/patch-friendly)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "endereco", source = "endereco")
    @Mapping(target = "senha", ignore = true) // tratada no decorator
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "versao", ignore = true)
    @Mapping(target = "ativo", ignore = true) // regra de negócio definida no domínio/serviço
    void updateEntity(@MappingTarget Cliente entity, ClienteRequestDTO dto, Endereco endereco);

    // Helper para montar o endereço completo com tolerância a nulos
    @Named("composeEnderecoCompleto")
    default String composeEnderecoCompleto(Endereco endereco) {
        if (endereco == null) return null;

        String logradouro = safe(endereco.getLogradouro());
        String numero = safe(endereco.getNumero());
        String complemento = safe(endereco.getComplemento());
        String bairro = safe(endereco.getBairro());
        String cidade = safe(endereco.getCidade());
        String uf = safe(endereco.getUf());
        String cep = safe(endereco.getCep());

        String comp = complemento.isEmpty() ? "" : ", " + complemento;

        // Ex.: Rua X, 123, Bloco A - Centro, São Paulo/SP, CEP: 01234-567
        return String.format("%s, %s%s - %s, %s/%s, CEP: %s",
                logradouro, numero, comp, bairro, cidade, uf, cep).replaceAll("\\s+,", ",").trim();
    }

    private static String safe(String v) {
        return v == null ? "" : v.trim();
    }
}
