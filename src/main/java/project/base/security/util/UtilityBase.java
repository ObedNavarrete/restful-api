package project.base.security.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import project.base.security.entity.Usuario;
import project.base.security.repository.UsuarioRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class UtilityBase {
    @Autowired HttpServletRequest request;
    @Autowired
    UsuarioRepository usuarioRepository;

    // creadoPor
    public Integer creadoPor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer id = usuarioRepository.obtenerIdUsuarioLogueado(authentication.getName());
        return id;
    }

    // creadoEl
    public Date creadoEl() {
        return new Date();
    }

    // creadoEnIp
    public String creadoEnIp() {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    // modificadoPor
    public Integer modificadoPor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer id = usuarioRepository.obtenerIdUsuarioLogueado(authentication.getName());
        return id;
    }

    // modificadoEl
    public Date modificadoEl() {
        return new Date();
    }

    // modificadoEnIp
    public String modificadoEnIp() {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    // randomUUID
    public String randomUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    // AddDaysToDate
    public static Date agregarDiasFecha(Date date, int days){
        if (days==0) return date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
    
    // AddMonthsToDate
    public static Date agregarMesesFecha(Date date, int months){
        if (months==0) return date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    // Verificar si el usuario logueado tiene el rol
    public Map<String, Boolean> usuarioLogeadoTieneRol(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Boolean> response = new java.util.HashMap<>();
        response.put("hasRole", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role)));
        return response;
    }

    // Verificar si el usuario tiene el rol
    // Ejenplo de uso => Boolean result = this.idUserHasRole(1,"ROLE_CUSTOMER");
    public Boolean idUsuarioTieneRol(Integer id, String rol) {
        Boolean response = false;
        Usuario user = usuarioRepository.findByPasivoIsFalseAndId(id);
        if (user == null) {
            return false;
        }
        response = user.getRoles().stream().anyMatch(r -> r.getNombre().equals(rol));
        return response;
    }

    // Verificar si el usuario tiene el uno de los roles
    // Ejenplo de uso => Boolean result = this.idUserHasAnyRole(1, new String[]{"ROLE_CUSTOMER", "ROLE_MANAGER"});
    public Boolean idUsuarioTieneElRol(Integer id, String[] roles) {
        Boolean response = false;
        Usuario user = usuarioRepository.findByPasivoIsFalseAndId(id);
        if (user == null) {
            return false;
        }
        for (String rol : roles) {
            response = user.getRoles().stream().anyMatch(r -> r.getNombre().equals(rol));
            if (response) {
                break;
            }
        }
        return response;
    }

    // Verificar si el usuario sigue siendo válido
    public boolean esUsuarioValido() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer id = usuarioRepository.obtenerIdUsuarioLogueado(authentication.getName());
        if (id == null) {
            return false;
        } else {
            return true;
        }
    }
}
