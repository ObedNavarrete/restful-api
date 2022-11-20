package project.base.security.util;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project.base.security.dto.UserDTO;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class EntityBase {
    private Date creadoEl;
    private Date modificadoEl;
    private Integer creadoPor;
    private Integer modificadoPor;
    private String creadoEnIp;
    private String modificadoEnIp;
    private Boolean pasivo;
}
