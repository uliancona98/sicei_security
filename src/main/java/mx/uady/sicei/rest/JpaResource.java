package mx.uady.sicei.resource;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Profesor;
import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.repository.AlumnoRepository;
import mx.uady.sicei.repository.ProfesorRepository;

@RestController
public class JpaResource {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @GetMapping("/login")
    public ResponseEntity<String> getLogin() {
        return ResponseEntity.ok("abierto");
    }
    @GetMapping("/quienSoy")
    public ResponseEntity<Usuario> getQuienSoy() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(usuario);
    }

}