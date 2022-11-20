package project.base.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.base.security.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombre(String name);
    Rol findByPasivoIsFalseAndNombre(String name);
}
