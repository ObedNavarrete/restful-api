package project.base.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.base.security.dto.UsuarioPassDTO;
import project.base.security.service.UsuarioService;

import java.net.URI;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Slf4j
public class PublicController {
    private final UsuarioService usuarioService;
}
