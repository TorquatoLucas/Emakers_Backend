package emakers.backend.mapper;

import org.mapstruct.Mapper;

import emakers.backend.dto.UsuarioDto;
import emakers.backend.dto.UsuarioResponse;
import emakers.backend.model.Usuario;


@Mapper (componentModel = "spring")
public interface UsuarioMapper {

    
    UsuarioDto tDto(Usuario usuario);
    Usuario toUsuario(UsuarioDto usuarioDto);
    UsuarioResponse toUsuarioResponse(Usuario usuario);

}
