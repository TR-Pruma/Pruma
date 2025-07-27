package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.AssinaturaDigitalRequestDTO;
import com.br.pruma.application.dto.response.AssinaturaDigitalResponseDTO;
import com.br.pruma.core.domain.AssinaturaDigital;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Documento;
import com.br.pruma.core.domain.TipoUsuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {Cliente.class, TipoUsuario.class, Documento.class})
public interface AssinaturaDigitalMapper {

    @Mapping(target = "cliente", expression = "java(new Cliente(dto.getClienteCpf()))")
    @Mapping(target = "tipoUsuario", expression = "java(new TipoUsuario(dto.getTipoUsuarioId()))")
    @Mapping(target = "documento", expression = "java(new Documento(dto.getDocumentoId()))")
    @Mapping(target = "dataHora", ignore = true) // Seta no service
    AssinaturaDigital toEntity(AssinaturaDigitalRequestDTO dto);

    @Mapping(source = "cliente.clienteCpf", target = "clienteCpf")
    @Mapping(source = "tipoUsuario.tipoUsuario", target = "tipoUsuarioId")
    @Mapping(source = "documento.documentoId", target = "documentoId")
    AssinaturaDigitalResponseDTO toResponseDTO(AssinaturaDigital entity);
}
