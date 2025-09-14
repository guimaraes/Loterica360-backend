package com.loteria360.mapper;

import com.loteria360.domain.dto.AbrirTurnoRequest;
import com.loteria360.domain.dto.TurnoResponse;
import com.loteria360.domain.model.Turno;
import com.loteria360.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface TurnoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "abertoEm", ignore = true)
    @Mapping(target = "fechadoEm", ignore = true)
    @Mapping(target = "valorFechamento", ignore = true)
    @Mapping(target = "status", ignore = true)
    Turno toEntity(AbrirTurnoRequest request);

    TurnoResponse toResponse(Turno turno);
}
