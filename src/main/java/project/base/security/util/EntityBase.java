package project.base.security.util;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project.base.security.dto.UserDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class EntityBase {
    private LocalDate creadoEl;
    private LocalDate modificadoEl;
    private Integer creadoPor;
    private Integer modificadoPor;
    private String creadoEnIp;
    private String modificadoEnIp;
    private Boolean pasivo;
}
