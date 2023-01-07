package project.base.security;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.base.security.dto.UsuarioPassDTO;
import project.base.security.entity.Rol;
import project.base.security.service.UsuarioService;

@SpringBootApplication
public class SecurityApplication {

    /**
     * @by: Obed Isaí Navarrete Díaz
     * @phone: +50584962664 || 0050584962664
     * @email: ndiazobed@gmail.com
     */

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
        System.out.println("SecurityApplication started...");
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    CommandLineRunner runner(UsuarioService usersService) {
        return args -> {
            usersService.guardarRol(new Rol(null, "ROLE_SUPER_ADMIN", false));

            usersService.guardar(
                    new UsuarioPassDTO(null,
                            "Obed Navarrete", "84962664", "ndiazobed@gmail.com",
                            "1234", true));

            usersService.agregarRolAlUsuario("ndiazobed@gmail.com", "ROLE_SUPER_ADMIN");
        };
    }*/

}
