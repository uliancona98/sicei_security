package mx.uady.sicei.resource;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Profesor;
import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.repository.AlumnoRepository;
import mx.uady.sicei.repository.ProfesorRepository;
import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;



import java.util.Collections;
import mx.uady.sicei.exception.NotFoundException;
import mx.uady.sicei.model.Licenciatura;
import mx.uady.sicei.model.request.AlumnoRequest;
import mx.uady.sicei.service.AlumnoSerivce;
@RestController
public class JpaResource {

    @Autowired
    private AlumnoSerivce alumnoService;
    @PostMapping("/login")
    public ResponseEntity<String> getLogin() {

        return ResponseEntity.ok("abierto");
    }

    @GetMapping("/quienSoy")
    //devuelve al usuario logeado
    public ResponseEntity<Usuario> getQuienSoy() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(usuario);
    }

    @PostMapping("/logout")
    //se bora el token de la base de datos y ya
    public ResponseEntity<String> logout() {
        //Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //return ResponseEntity.ok().body(usuario);
        return ResponseEntity.ok("cerrado");
    }

    @PostMapping("/register")
    //crea un usuario con un nuevo token
    public ResponseEntity<Alumno> register(@RequestBody @Valid AlumnoRequest request) throws URISyntaxException{
        //Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //return ResponseEntity.ok().body(usuario);
        Alumno alumno = alumnoService.crearAlumno(request);
        // 201 Created
        // Header: Location
        return ResponseEntity
            .created(new URI("/alumnos/" + alumno.getId()))
            .body(alumno);

    }
}