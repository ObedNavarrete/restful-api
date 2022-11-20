package project.base.security.service;

import project.base.security.dto.ResponseDTO;
import project.base.security.dto.UserDTO;
import project.base.security.dto.UsuarioPassDTO;
import project.base.security.entity.Rol;

import java.util.Map;

public interface UsuarioService {
    ResponseDTO guardar(UsuarioPassDTO user);

    ResponseDTO guardarSuperAdmin(UsuarioPassDTO user);

    ResponseDTO guardarAdmin(UsuarioPassDTO user);

    ResponseDTO obtenerPorId(Integer id);

    UserDTO obtenerPorEmail(String email);

    UserDTO obtenerPorTelefono(String phone);

    ResponseDTO obtenerTodos(int page, int size);

    ResponseDTO actualizar(Integer id, UserDTO user);

    ResponseDTO eliminar(Integer id);

    /**
     * By: Obed Navarrete
     * Date: 29/10/2022
     * This methods is used for the role save and assign to the user
     * @param rol to be saved
     * @return the persisted entity of the role saved
     */

    ResponseDTO guardarRol(Rol rol);

    Map<String, String> agregarRolAlUsuario(String email, String roleName);

    Map<String, String> eliminarRolDelUsuario(String email, String roleName);
}
