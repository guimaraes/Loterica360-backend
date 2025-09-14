package com.loteria360.mapper;

import com.loteria360.domain.dto.CriarUsuarioRequest;
import com.loteria360.domain.dto.UsuarioResponse;
import com.loteria360.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "senhaHash", source = "senha")
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    Usuario toEntity(CriarUsuarioRequest request);

    UsuarioResponse toResponse(Usuario usuario);
}
