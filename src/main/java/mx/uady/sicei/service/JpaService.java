package mx.uady.sicei.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.sicei.exception.NotFoundException;
import javax.transaction.Transactional;
import java.util.UUID;
import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Usuario;

import mx.uady.sicei.model.request.AlumnoRequest;
import mx.uady.sicei.model.Tutoria;
import mx.uady.sicei.model.Equipo;

import mx.uady.sicei.repository.AlumnoRepository;
import mx.uady.sicei.repository.TutoriaRepository;
import mx.uady.sicei.repository.UsuarioRepository;
import mx.uady.sicei.repository.EquipoRepository;

import java.util.UUID;

@Service
public class JpaService {

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TutoriaRepository tutoriaRepository;
    @Autowired
    private EquipoRepository equipoRepository;

    public Alumno getAlumno(Integer id) {
        return alumnoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("No existe este alumno :v"));
    }
}