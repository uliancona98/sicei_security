package mx.uady.sicei.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.sicei.exception.NotFoundException;

import mx.uady.sicei.model.Profesor;
import mx.uady.sicei.model.request.ProfesorRequest;
import mx.uady.sicei.model.Tutoria;
import mx.uady.sicei.repository.ProfesorRepository;
import mx.uady.sicei.repository.TutoriaRepository;

@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private TutoriaRepository tutoriaRepository;

    public List<Profesor> obtenerProfesores() {

        List<Profesor> profesores = new LinkedList<>();
        profesorRepository.findAll().iterator().forEachRemaining(profesores::add); // SELECT(id, nombre)
        
        return profesores;
    }

    public Profesor crearProfesor(ProfesorRequest request) {
        Profesor profesor = new Profesor();

        profesor.setNombre(request.getNombre());
        profesor.setHoras(request.getHoras());
        profesor = profesorRepository.save(profesor); // INSERT

        return profesor;
    }

    public Profesor getProfesor(Integer id) {

        return profesorRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("No existe ese profesor :v"));
    }

    public Profesor editarProfesor(Integer id, ProfesorRequest request) {

        return profesorRepository.findById(id)
        .map(profesor -> {
            profesor.setNombre(request.getNombre());
            profesor.setHoras(request.getHoras());
            return profesorRepository.save(profesor);
        })
        .orElseThrow(() -> new NotFoundException("No existe ese profesor :v"));
    }

    public String borrarProfesor(Integer id) {

        List<Profesor> profesores = new LinkedList<>();
        profesorRepository.findAll().iterator().forEachRemaining(profesores::add);
        if(profesores.size() < id || id <= 0){
            throw new NotFoundException("No existe ese profesor :v");
        }

        List<Tutoria> tutoriasProfesor = tutoriaRepository.findByProfesorId(id);

        if(tutoriasProfesor.isEmpty()){
            profesorRepository.deleteById(id);
            return "Profesor Borrado";
        } else {
            return "Profesor "+id+" No se pudo borrar ya que tiene tutorias asignadas";
        }
    }
    
}