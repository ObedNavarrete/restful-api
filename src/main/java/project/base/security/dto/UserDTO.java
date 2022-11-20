package project.base.security.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class UserDTO {
    private Integer id;
    private String nombre;
    private String telefono;
    private String email;
    private Boolean activo;
    private Collection<RolDTO> roles = new ArrayList<>();
}
