package com.loteria360.domain.mapper;

import com.loteria360.domain.dto.UsuarioRequest;
import com.loteria360.domain.dto.UsuarioResponse;
import com.loteria360.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "senhaHash", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    Usuario toEntity(UsuarioRequest request);
    
    UsuarioResponse toResponse(Usuario usuario);
}
