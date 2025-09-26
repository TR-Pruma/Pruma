package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.PermissaoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.PermissaoUsuarioResponseDTO;
import com.br.pruma.core.domain.PermissaoUsuario;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.TipoUsuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PermissaoUsuarioMapper {

    /* =======================
       Converte DTO -> Entidade
       ======================= */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", source = "clienteCpf", qualifiedByName = "clienteFromCpf")
    @Mapping(target = "tipoUsuario", source = "tipoUsuarioId", qualifiedByName = "tipoUsuarioFromId")
    PermissaoUsuario toEntity(PermissaoUsuarioRequestDTO dto);

    /* =======================
       Converte Entidade -> ResponseDTO
       ======================= */
    @Mapping(target = "clienteCpf", source = "cliente.cpf")
    @Mapping(target = "clienteNome", source = "cliente.nome") // ajuste conforme atributo real
    @Mapping(target = "tipoUsuarioId", source = "tipoUsuario.id")
    @Mapping(target = "tipoUsuarioDescricao", source = "tipoUsuario.descricao") // ajuste conforme atributo real
    PermissaoUsuarioResponseDTO toResponseDTO(PermissaoUsuario entity);


    /* =======================
       Métodos auxiliares
       ======================= */
    @Named("clienteFromCpf")
    default Cliente clienteFromCpf(String cpf) {
        if (cpf == null) return null;
        Cliente c = new Cliente();
        c.setCpf(cpf);
        return c;
    }

    @Named("tipoUsuarioFromId")
    default TipoUsuario tipoUsuarioFromId(Integer id) {
        if (id == null) return null;
        TipoUsuario t = new TipoUsuario();
        t.setId(id);
        return t;
    }
}
