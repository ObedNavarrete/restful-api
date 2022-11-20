package project.base.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rol")
@Data @AllArgsConstructor @NoArgsConstructor
@SequenceGenerator(name = "rol_id_seq", sequenceName = "rol_id_seq", allocationSize = 1)
public class Rol implements Serializable {
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_id_seq")
    private Integer id;
    private String nombre;
    private Boolean pasivo = false;
}
