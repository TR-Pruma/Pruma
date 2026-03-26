package com.br.pruma.application.mapper;

import com.br.pruma.application.dto.request.AssinaturaDigitalRequestDTO;
import com.br.pruma.application.dto.response.AssinaturaDigitalResponseDTO;
import com.br.pruma.core.domain.AssinaturaDigital;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Documento;
import com.br.pruma.core.domain.TipoUsuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", imports = {Cliente.class, TipoUsuario.class, Documento.class})
public interface AssinaturaDigitalMapper {

    AssinaturaDigitalMapper INSTANCE = Mappers.getMapper(AssinaturaDigitalMapper.class);

    AssinaturaDigital toEntity(AssinaturaDigitalRequestDTO dto);

    AssinaturaDigitalResponseDTO toResponseDTO(AssinaturaDigital entity);
}
