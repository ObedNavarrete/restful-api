package project.base.security.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UsuarioPassDTO {
    private Integer id;

    @NotNull(message = "First name is required")
    private String nombre;
    @NotNull(message = "Phone is required")
    @Pattern(regexp = "^(\\+?\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", message = "Phone number is not valid")
    @Length(min = 8, max = 8, message = "Phone number must be 10 digits")
    private String telefono;
    @NotNull(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email is not valid")
    private String email;
    @NotNull(message = "Password is required")
    private String password;
    private Boolean activo;
}
