package project.base.security.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UsuarioPassDTO {
    private Integer id;

    @NotNull(message = "El campo nombre no puede ser nulo")
    private String nombre;
    @NotNull(message = "El campo telefono no puede ser nulo")
    @Length(min = 8, max = 8, message = "El campo telefono debe tener 8 caracteres")
    @Pattern(regexp = "^[0-9]*$", message = "El campo telefono debe ser numérico")
    private String telefono;
    @NotNull(message = "El campo email no puede ser nulo")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "El campo email debe ser un email válido")
    private String email;
    @NotNull(message = "La contraseña no puede ser nula")
    private String password;
    private Boolean activo;
}
