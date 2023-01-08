package project.base.security.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UsuarioRolDTO {
    @NotNull(message = "El campo id no puede ser nulo")
    private Integer idUsuario;
    @NotNull(message = "El nombre del rol no puede ser nulo")
    private String nombreRol;
}
