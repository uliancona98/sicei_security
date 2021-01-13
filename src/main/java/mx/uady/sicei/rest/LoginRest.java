package mx.uady.sicei.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.service.AlumnoService;
import mx.uady.sicei.service.UsuarioService;
import mx.uady.sicei.config.JwtTokenUtil;
import mx.uady.sicei.model.request.LoginRequest;
import mx.uady.sicei.model.request.AlumnoRequest;
import mx.uady.sicei.model.JwtResponse;


@RestController
@RequestMapping("/api")
public class LoginRest {

    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    // POST /login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) throws RuntimeException{

        Usuario usuario = usuarioService.getUsuario(request.getUsuario());

        if (usuario == null) {
            String errMessage = "El usuario o la contrasena son incorrectos";
            return ResponseEntity.ok(errMessage);
        }

        String token = jwtTokenUtil.generateToken(usuario);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/quienSoy")
    //devuelve al usuario logeado
    public ResponseEntity<Usuario> getQuienSoy() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(usuario);
    }

    @PostMapping("/signout")
    public ResponseEntity<String> logout() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // usuario.setToken(null);
        // usuarioRepository.save(usuario);
        return ResponseEntity.ok().body("Log out correcto");
    }

    @PostMapping("/register")
    //crea un usuario con un nuevo token
    public ResponseEntity register(@RequestBody @Valid AlumnoRequest request) throws URISyntaxException{
        
        Alumno alumno = alumnoService.crearAlumno(request);

        if(alumno != null) {
            return ResponseEntity
            .created(new URI("/alumnos/" + alumno.getId()))
            .body(alumno);
        } else {
            return ResponseEntity.badRequest().body("Ya existe un usuario con este usuario");        
        }
    }
}