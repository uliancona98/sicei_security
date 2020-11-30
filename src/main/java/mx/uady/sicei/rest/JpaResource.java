package mx.uady.sicei.resource;

import java.util.LinkedList;
import java.util.List;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Profesor;
import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.repository.AlumnoRepository;
import mx.uady.sicei.repository.ProfesorRepository;
import mx.uady.sicei.repository.UsuarioRepository;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import mx.uady.sicei.exception.NotFoundException;
import mx.uady.sicei.model.Licenciatura;
import mx.uady.sicei.model.request.AlumnoRequest;
import mx.uady.sicei.service.AlumnoSerivce;
import mx.uady.sicei.service.UsuarioService;
import mx.uady.sicei.repository.UsuarioRepository;

@RestController
public class JpaResource {

    @Autowired
    private AlumnoSerivce alumnoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    /*@PostMapping("/login")
    public ResponseEntity<String> getLogin() {

        return ResponseEntity.ok("abierto");
    }*/

    @GetMapping("/quienSoy")
    //devuelve al usuario logeado
    public ResponseEntity<Usuario> getQuienSoy() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(usuario);
    }

    /*@PostMapping("/logout")
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {        
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        usuario.setToken(null);
        usuarioRepository.save(usuario);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
          
        return "redirect:/login?logout";
    }*/

    @PostMapping("/signout")
    public ResponseEntity<String> logout() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        usuario.setToken(null);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().body("Log out correcto");
    }

    /*@PostMapping("/logout")
    public ResponseEntity<String> logout() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        usuario.setToken(null);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().body("Log out correcto");
    }*/
    @PostMapping("/register")
    //crea un usuario con un nuevo token
    public ResponseEntity register(@RequestBody @Valid AlumnoRequest request) throws URISyntaxException{
        //Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //return ResponseEntity.ok().body(usuario);
        Alumno alumno = alumnoService.crearAlumno(request);
        // 201 Created
        // Header: Location
        if(alumno!=null){
            return ResponseEntity
            .created(new URI("/alumnos/" + alumno.getId()))
            .body(alumno);
        }else{
            return ResponseEntity.badRequest().body("Ya existe un usuario con este usuario");        

        }


    }
}