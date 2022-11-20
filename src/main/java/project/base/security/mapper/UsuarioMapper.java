package project.base.security.mapper;

import org.mapstruct.Mapper;
import project.base.security.dto.UserDTO;
import project.base.security.dto.UsuarioPassDTO;
import project.base.security.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UserDTO userDTO);
    UserDTO toDTO(Usuario usuario);

    Usuario withPassToEtity(UsuarioPassDTO usuarioPassDTO);
}
