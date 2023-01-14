package project.base.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import project.base.security.entity.Usuario;
import project.base.security.repository.UsuarioRepository;
import project.base.security.service.UsuarioService;
import project.base.security.util.Constantes;

import javax.servlet.ServletContext;
import java.util.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j @Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.FilterChain filterChain) throws javax.servlet.ServletException, java.io.IOException {
        if (request.getServletPath().equals("/login") || request.getServletPath().equals("/token/refresh")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    String secretKeyJwt = Constantes.SECRET_KEY_JWT;
                    Algorithm algorithm = Algorithm.HMAC256(secretKeyJwt.getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();

                    if(usuarioRepository==null){
                        ServletContext servletContext = request.getServletContext();
                        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                        try {
                            assert webApplicationContext != null;
                            usuarioRepository = webApplicationContext.getBean(UsuarioRepository.class);
                        } catch (AssertionError e) {
                            log.error("webApplicationContext is null");
                            response.setStatus(INTERNAL_SERVER_ERROR.value());
                            Map<String, String> error = new HashMap<>();
                            error.put("webApplicationContext", "El webApplicationContext es null");
                            response.setContentType(APPLICATION_JSON_VALUE);
                            new ObjectMapper().writeValue(response.getOutputStream(), error);
                            return;
                        }
                    }

                    Integer id = this.usuarioRepository.obtenerId(Integer.parseInt(username));
                    if (id == null) {
                        log.error("Usuario no encontrado");
                        response.setHeader("error", "EL usuario cambio de estado");
                        response.setStatus(FORBIDDEN.value());
                        Map<String, String> error = new HashMap<>();
                        error.put("fatal_user", "El status de este usuario ha cambiado, por favor actualice la página");
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), error);
                        return;
                    }

                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    Arrays.stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    log.error("Error logging in: {}", exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    // response.sendError(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }

    }

}
