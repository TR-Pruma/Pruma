package com.br.pruma.application.mapper;

import com.br.pruma.core.domain.TipoUsuario;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * Utilitario de conversao para o enum TipoUsuario.
 * TipoUsuario e um enum (nao uma entidade JPA), portanto nao possui mapper MapStruct
 * de entidade. Esta classe fornece helpers reutilizaveis para outros mappers.
 */
@Component
public class TipoUsuarioMapper {

    @Named("tipoUsuarioToName")
    public String toName(TipoUsuario tipo) {
        return tipo == null ? null : tipo.name();
    }

    @Named("tipoUsuarioFromName")
    public TipoUsuario fromName(String name) {
        return name == null ? null : TipoUsuario.valueOf(name);
    }

    @Named("tipoUsuarioFromId")
    public TipoUsuario fromId(Integer id) {
        if (id == null) return null;
        TipoUsuario[] values = TipoUsuario.values();
        int idx = id - 1;
        return (idx >= 0 && idx < values.length) ? values[idx] : null;
    }
}
