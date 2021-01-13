package mx.uady.sicei.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.sicei.exception.NotFoundException;
import mx.uady.sicei.model.Alumno;

import mx.uady.sicei.repository.AlumnoRepository;

@Service
public class JpaService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    public Alumno getAlumno(Integer id) {
        return alumnoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("No existe este alumno"));
    }
}