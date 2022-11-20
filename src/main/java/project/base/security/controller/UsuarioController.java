package project.base.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.base.security.dto.UsuarioRolDTO;
import project.base.security.dto.UserDTO;
import project.base.security.dto.UsuarioPassDTO;
import project.base.security.entity.Rol;
import project.base.security.service.UsuarioService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor @Slf4j
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/usuario")
    public ResponseEntity<?> obtenerTodos(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        return ResponseEntity.ok(usuarioService.obtenerTodos(page, size));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @GetMapping("/usuario/obtenerPorEmail/{email}/")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.obtenerPorEmail(email));
    }

    @GetMapping("/usuario/obtenerPorTelefono/{phone}")
    public ResponseEntity<?> getUserByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(usuarioService.obtenerPorTelefono(phone));
    }

    @PostMapping("/usuario/guardar")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UsuarioPassDTO user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toUriString());
        return ResponseEntity.created(uri).body(usuarioService.guardar(user));
    }

    @PostMapping("/usuario/guardarSuperAdmin")
    public ResponseEntity<?> saveSuperAdmin(@Valid @RequestBody UsuarioPassDTO user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/saveSuperAdmin").toUriString());
        return ResponseEntity.created(uri).body(usuarioService.guardarSuperAdmin(user));
    }

    @PostMapping("/usuario/guardarAdmin")
    public ResponseEntity<?> saveAdmin(@Valid @RequestBody UsuarioPassDTO user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/saveAdmin").toUriString());
        return ResponseEntity.created(uri).body(usuarioService.guardarAdmin(user));
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/usuario/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.eliminar(id));
    }

    /*
     * @RelativeToRole
     * @Path: src\main\java\project\base\security\entity\Rol.java
     */
    @PostMapping("/rol")
    public ResponseEntity<?> saveRole(@RequestBody Rol rol) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(usuarioService.guardarRol(rol));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    @PostMapping("/rol/agregarRolAlUsuario")
    public ResponseEntity<?> agregarRolAlUsuario(@Valid @RequestBody UsuarioRolDTO form) {
        return ResponseEntity.ok().body(usuarioService.agregarRolAlUsuario(form.getEmail(), form.getNombreRol()));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    @DeleteMapping("/role/eliminarRolDelUsuario")
    public ResponseEntity<?> eliminarRolDelUsuario(@Valid @RequestBody UsuarioRolDTO form) {
        return ResponseEntity.ok().body(usuarioService.eliminarRolDelUsuario(form.getEmail(), form.getNombreRol()));
    }
}
