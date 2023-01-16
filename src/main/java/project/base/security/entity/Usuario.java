package project.base.security.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(
        name = "usuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "telefono")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "usuario_id_seq", sequenceName = "usuario_id_seq", allocationSize = 1)
public class Usuario implements Serializable {
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_id_seq")
    private Integer id;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol",
    joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Collection<Rol> roles = new ArrayList<>();

    private String nombre;
    private String telefono;
    private String email;
    private String password;
    private Boolean activo;

    /*
    // Campos de auditoria
     */
    private Boolean pasivo = false;
    private LocalDateTime creadoEl;
    private LocalDateTime modificadoEl;
    private Integer creadoPor;
    private Integer modificadoPor;
    private String creadoEnIp;
    private String modificadoEnIp;
}
