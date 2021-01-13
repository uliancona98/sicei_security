package mx.uady.sicei.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "tutorias")
public class Tutoria {

    @EmbeddedId
    private TutoriaId id;

    @ManyToOne(optional=false)
    @JoinColumn(name="id_alumno",referencedColumnName="id", insertable=false, updatable=false)
    Alumno alumno;

    @ManyToOne(optional=false)
    @JoinColumn(name="id_profesor",referencedColumnName="id", insertable=false, updatable=false)
    Profesor profesor;

    @Column(name = "horas")
    private Integer horas;

    @Embeddable
    public static class TutoriaId implements Serializable {

        private static final long serialVersionUID = 1L;

        @Column(name = "id_alumno", insertable=false,updatable=false)
        private Integer alumnoId;

        @Column(name = "id_profesor", insertable=false, updatable=false)
        private Integer profesorId;

        public TutoriaId() {
            //empty
         }

        /**
         * @return the alumnoId
         */
        public Integer getAlumnoId() {
            return alumnoId;
        }/**
         * @param alumnoId the alumnoId to set
         */
        public void setAlumnoId(Integer alumnoId) {
            this.alumnoId = alumnoId;
        }/**
         * @return the profesorId
         */
        public Integer getProfesorId() {
            return profesorId;
        }/**
         * @param profesorId the profesorId to set
         */
        public void setProfesorId(Integer profesorId) {
            this.profesorId = profesorId;
        }
    }

    public Tutoria() {
        //empty
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setId(TutoriaId id) {
        this.id = id;
    }

    public TutoriaId getId() {
        return id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", alumno='" + getAlumno() + "'" +
            ", profesor='" + getProfesor() + "'" +
            ", horas='" + getHoras() + "'" +
            "}";
    }
    


}