package project.base.security.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.base.security.dto.PageResponse;
import project.base.security.dto.ResponseDTO;
import project.base.security.dto.UserDTO;
import project.base.security.dto.UsuarioPassDTO;
import project.base.security.entity.Rol;
import project.base.security.entity.Usuario;
import project.base.security.mapper.UsuarioMapper;
import project.base.security.repository.RolRepository;
import project.base.security.repository.UsuarioRepository;
import project.base.security.service.UsuarioService;
import project.base.security.util.UtilityBase;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsuarioServiceImpl extends UtilityBase implements UsuarioService, UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    /**
     * By: Obed Navarrete
     * Date: 29/10/2022
     * This method is used for the authentication of the user
     * @param email or phone of the user to be authenticated
     * @return the persisted entity of the user authenticated or null if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByPasivoIsFalseAndActivoIsTrueAndEmail(email);

        if (usuario == null) {
            log.error("Email not found in the database");
            usuario = usuarioRepository.findByPasivoIsFalseAndActivoIsTrueAndTelefono(email);
            if (usuario == null) {
                log.error("Phone not found in the database");
                throw new UsernameNotFoundException("Email or Phone not found in the database");
            }
        }

        log.info("User found in the database: {}", email);
        Collection<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());


        return new org.springframework.security.core.userdetails
                .User(usuario.getEmail(), usuario.getPassword(), authorities);
    }

    @Override
    public ResponseDTO guardar(UsuarioPassDTO user) {
        log.info("save new user {} to the database ", user.getEmail());

        ResponseDTO response = new ResponseDTO();
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Usuario usuarioEntity = usuarioMapper.withPassToEtity(user);
            usuarioEntity.setActivo(true);
            usuarioEntity.setCreadoEl(new Date());
            usuarioEntity.setCreadoEnIp("localhost");
            usuarioRepository.save(usuarioEntity);

            response.setStatus("200");
            response.setMessage("success");
            response.setComment("User saved successfully");
            response.setData(usuarioMapper.toDTO(usuarioEntity));
            return response;
        } catch (Exception e) {
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("User save failed, comment: " + e.getMessage());
            return response;
        }
    }

    @Override
    public ResponseDTO guardarSuperAdmin(UsuarioPassDTO user) {
        log.info("save new user {} to the database ", user.getEmail());

        if ((user.getEmail().isEmpty() || user.getEmail() == null) && (user.getTelefono().isEmpty() || user.getTelefono() == null)) {
            return new ResponseDTO("500", "error", "User save failed, comment: Email or Phone is required", null);
        }

        ResponseDTO response = new ResponseDTO();
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Usuario usuarioEntity = usuarioMapper.withPassToEtity(user);
            usuarioEntity.setActivo(true);
            usuarioEntity.setCreadoPor(this.creadoPor());
            usuarioEntity.setCreadoEl(new Date());
            usuarioEntity.setCreadoEnIp(this.creadoEnIp());
            Usuario nu = usuarioRepository.save(usuarioEntity);
            nu.getRoles().add(rolRepository.findByNombre("ROLE_SUPER_ADMIN"));

            response.setStatus("201");
            response.setMessage("success");
            response.setComment("User saved successfully");
            response.setData(usuarioMapper.toDTO(usuarioEntity));
            return response;
        } catch (Exception e) {
            log.error("User save failed, comment: " + e.getMessage());
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("User save failed, comment: " + e.getMessage());
            return response;
        }
    }

    @Override
    public ResponseDTO guardarAdmin(UsuarioPassDTO user) {
        log.info("save new user {} to the database ", user.getEmail());

        if ((user.getEmail().isEmpty() || user.getEmail() == null) && (user.getTelefono().isEmpty() || user.getTelefono() == null)) {
            return new ResponseDTO("500", "error", "User save failed, comment: Email or Phone is required", null);
        }

        ResponseDTO response = new ResponseDTO();
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Usuario usuarioEntity = usuarioMapper.withPassToEtity(user);
            usuarioEntity.setActivo(true);
            usuarioEntity.setCreadoPor(this.creadoPor());
            usuarioEntity.setCreadoEl(new Date());
            usuarioEntity.setCreadoEnIp(this.creadoEnIp());
            Usuario nu = usuarioRepository.save(usuarioEntity);
            nu.getRoles().add(rolRepository.findByNombre("ROLE_ADMIN"));

            response.setStatus("201");
            response.setMessage("success");
            response.setComment("User saved successfully");
            response.setData(usuarioMapper.toDTO(usuarioEntity));

            log.info("User saved successfully");
            return response;
        } catch (Exception e) {
            log.error("User save failed, comment: " + e.getMessage());
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("User save failed, comment: " + e.getMessage());
            return response;
        }
    }

    @Override
    public ResponseDTO obtenerPorId(Integer id) {
        log.info("get user by id {} ", id);
        Usuario user = usuarioRepository.findById(id).orElse(null);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found");
        }

        return new ResponseDTO("200", "success", "User found", usuarioMapper.toDTO(user));
    }

    @Override
    public UserDTO obtenerPorEmail(String email) {
        log.info("get user by email {} ", email);
        Usuario user = usuarioRepository.findByPasivoIsFalseAndActivoIsTrueAndEmail(email);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found");
        }

        return usuarioMapper.toDTO(user);
    }

    @Override
    public UserDTO obtenerPorTelefono(String phone) {
        log.info("get user by phone {} ", phone);
        Usuario user = usuarioRepository.findByPasivoIsFalseAndActivoIsTrueAndTelefono(phone);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found");
        }

        return usuarioMapper.toDTO(user);
    }

    @Override
    public ResponseDTO obtenerTodos(int page, int size) {
        log.info("get all users");
        PageResponse pageResponse = new PageResponse();
        page = page - 1;

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<Usuario> users = usuarioRepository.findAllByPasivoIsFalse(pageable);
        List<Usuario> usuarioList = users.getContent();

        if (usuarioList.isEmpty()) {
            log.error("Users not found in the database");
            return new ResponseDTO("404", "error", "Users not found", null);
        }

        List<UserDTO> userDTOList = usuarioList.stream().map(usuarioMapper::toDTO).collect(Collectors.toList());

        pageResponse.setContent(userDTOList);
        pageResponse.setTotalElements(users.getTotalElements());
        pageResponse.setTotalPages(users.getTotalPages());
        pageResponse.setPage(users.getNumber() + 1);
        pageResponse.setSize(users.getSize());
        pageResponse.setLast(users.isLast());

        return new ResponseDTO("200", "success", "Users found", pageResponse);
    }

    @Override
    public ResponseDTO actualizar(Integer id, UserDTO user) {
        log.info("update user {} to the database ", user.toString());
        ResponseDTO response = new ResponseDTO();

        Usuario usuarioEntity = usuarioRepository.findById(id).orElse(null);
        if (usuarioEntity == null) {
            log.error("User not found in the database");
            return new ResponseDTO("404", "error", "User not found", null);
        }

        try {
            usuarioEntity.setNombre(user.getNombre());
            usuarioEntity.setTelefono(user.getTelefono());
            usuarioEntity.setEmail(user.getEmail());
            usuarioEntity.setModificadoEl(this.modificadoEl());
            usuarioEntity.setModificadoEnIp(this.modificadoEnIp());
            usuarioEntity.setModificadoPor(this.modificadoPor());
            usuarioEntity.setPasivo(false);
            usuarioEntity.setActivo(true);

            usuarioRepository.save(usuarioEntity);
            response.setStatus("200");
            response.setMessage("success");
            response.setComment("User updated successfully");
            response.setData(null);
            log.info("User updated successfully");
            return response;
        } catch (Exception e) {
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("User update failed, comment: " + e.getMessage());
            return response;
        }
    }

    @Override
    public ResponseDTO eliminar(Integer id) {
        log.info("delete user by id {} ", id);
        ResponseDTO response = new ResponseDTO();

        Usuario usuarioEntity = usuarioRepository.findByPasivoIsFalseAndId(id);
        if (usuarioEntity == null) {
            log.error("User not found in the database or already deleted");
            return new ResponseDTO("404", "error", "User not found", null);
        }

        try {
            usuarioEntity.setModificadoEl(this.modificadoEl());
            usuarioEntity.setModificadoEnIp(this.modificadoEnIp());
            usuarioEntity.setModificadoPor(this.modificadoPor());
            usuarioEntity.setPasivo(true);
            usuarioEntity.setActivo(false);

            log.info("Deleting user with email: {}", usuarioEntity.getEmail());

            usuarioRepository.save(usuarioEntity);
            response.setStatus("200");
            response.setMessage("success");
            response.setComment("User deleted successfully");
            response.setData(null);
            log.info("User deleted successfully");
            return response;
        } catch (Exception e) {
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("User delete failed, comment: " + e.getMessage());
            return response;
        }
    }

    /**
     * By: Obed Navarrete
     * Date: 29/10/2022
     * This methods is used for the role save and assign to the user
     * @param rol to be saved
     * @return the persisted entity of the role saved
     */
    @Override
    public ResponseDTO guardarRol(Rol rol) {
        log.info("save new role {} to the database ", rol.getNombre());
        ResponseDTO response = new ResponseDTO();
        try {
            rol.setPasivo(false);
            rolRepository.save(rol);
            response.setStatus("200");
            response.setMessage("success");
            response.setComment("Role saved successfully");
            response.setData(rol);
            return response;
        } catch (Exception e) {
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("Role save failed, comment: " + e.getMessage());
            return response;
        }
    }

    @Override
    public Map<String, String> agregarRolAlUsuario(String email, String roleName) {
        log.info("add role {} to user {} ", roleName, email);
        Map<String, String> response = new HashMap<>();

        Usuario usuario = usuarioRepository.findByEmail(email);
        Rol rol = rolRepository.findByNombre(roleName);

        if (usuario == null || rol == null) {
            log.error("User not found in the database");
            response.put("status", "404");
            response.put("message", "error");
            response.put("comment", "User or role not found");
            return response;
        }

        if (usuario.getRoles().contains(rol)) {
            log.error("User already has this role");
            response.put("status", "400");
            response.put("message", "error");
            response.put("comment", "User already has this role");
            return response;
        }

        try {
            usuario.getRoles().add(rol);
            usuarioRepository.save(usuario);
            response.put("status", "200");
            response.put("message", "success");
            response.put("comment", "Role added to user successfully");
            return response;
        } catch (Exception e) {
            response.put("status", "500");
            response.put("message", "error");
            response.put("comment", "Role add to user failed, comment: " + e.getMessage());
            return response;
        }
    }

    @Override
    public Map<String, String> eliminarRolDelUsuario(String email, String roleName) {
        log.info("delete role {} from user {} ", roleName, email);
        Map<String, String> response = new HashMap<>();

        Usuario usuario = usuarioRepository.findByEmail(email);
        Rol rol = rolRepository.findByNombre(roleName);

        if (usuario == null || rol == null) {
            log.error("User or role not found in the database");
            response.put("status", "404");
            response.put("message", "error");
            response.put("comment", "User or role not found");
            return response;
        }

        if (usuario.getRoles().contains(rol)) {
            log.info("Deleting role {} from user {}", roleName, email);
            usuario.getRoles().remove(rol);
            response.put("status", "200");
            response.put("message", "success");
            response.put("comment", "Role deleted successfully");
            return response;
        } else {
            log.error("User does not have the role");
            response.put("status", "404");
            response.put("message", "error");
            response.put("comment", "User does not have the role");
            return response;
        }
    }
}
