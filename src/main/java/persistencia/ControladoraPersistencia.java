/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logica.Carrera;
import logica.Carreraestudiante;
import logica.Estudiante;
import logica.InscripEstudianteMateria;
import logica.Materias;
import logica.NivelCursoEstudiante;
import logica.Semestre;
import logica.Usuario;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class ControladoraPersistencia {

    CarreraJpaController carreraJpaController = null;
    CarreraestudianteJpaController carreraestudianteJpaController = null;
    EstudianteJpaController estudianteJpaController = null;
    InscripEstudianteMateriaJpaController inscripEstudianteMateriaJpaController = null;
    MateriasJpaController materiasJpaController = null;
    NivelCursoEstudianteJpaController nivelCursoEstudianteJpaController = null;
    SemestreJpaController semestreJpaController = null;
    UsuarioJpaController usuarioJpaController=null;

    public ControladoraPersistencia() {

        carreraJpaController = new CarreraJpaController();
        carreraestudianteJpaController = new CarreraestudianteJpaController();
        estudianteJpaController = new EstudianteJpaController();
        inscripEstudianteMateriaJpaController = new InscripEstudianteMateriaJpaController();
        materiasJpaController = new MateriasJpaController();
        nivelCursoEstudianteJpaController = new NivelCursoEstudianteJpaController();
        semestreJpaController = new SemestreJpaController();
        usuarioJpaController=new UsuarioJpaController();

    }

    public void crearEstudiante(Estudiante estudiante) {
        estudianteJpaController.create(estudiante);
    }

    public List<Estudiante> listarEstudiantes() {
        return estudianteJpaController.findEstudianteEntities();
    }

    public void actualizarEstudiante(Estudiante estudiante) {
        try {
            estudianteJpaController.edit(estudiante);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarSemestreInicial(NivelCursoEstudiante nivelCursoEstudiante) {
        nivelCursoEstudianteJpaController.create(nivelCursoEstudiante);
    }

    public List<Materias> materiasDeSemestres() {
        return materiasJpaController.findMateriasEntities();
    }

    public void cargarMateriasEstudiante(InscripEstudianteMateria inscripEstudianteMateria) {
        inscripEstudianteMateriaJpaController.create(inscripEstudianteMateria);
    }

    public Semestre buscarSemestre(int semestre) {
        return semestreJpaController.findSemestre(semestre);
    }

    public List<NivelCursoEstudiante> listarEstudianteConSemestre() {
        return nivelCursoEstudianteJpaController.findNivelCursoEstudianteEntities();
    }

    public List<Materias> listarMaterias() {
        return materiasJpaController.findMateriasEntities();
    }

    public List<Semestre> listarSemestre() {
        return semestreJpaController.findSemestreEntities();
    }

    public Materias buscarMateriaPorId(int idMateria) {
        return materiasJpaController.findMaterias(idMateria);
    }

    public void actualizarMateria(Materias materia) {
        try {
            materiasJpaController.edit(materia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarMateria(int idMateria) {
        try {
            materiasJpaController.destroy(idMateria);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crearMateria(Materias materia) {
        materiasJpaController.create(materia);
    }

    public List<InscripEstudianteMateria> obtenerInscripEstudianteMateria() {
        return inscripEstudianteMateriaJpaController.findInscripEstudianteMateriaEntities();
    }

    public void actualizarInscripEstudianteMateria(InscripEstudianteMateria inscripcion) {
        try {
            inscripEstudianteMateriaJpaController.edit(inscripcion);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Carrera> listarCarreraImtel() {
        return carreraJpaController.findCarreraEntities();
    }

    public Carrera buscarCarrera(int idCarrera) {
        return carreraJpaController.findCarrera(idCarrera);
    }

    public void asignarCarreraAEstudiante(Carreraestudiante carreraestudiante) {
        carreraestudianteJpaController.create(carreraestudiante);
    }

    public List<Carreraestudiante> listarEstudianteConCarreras() {
        return carreraestudianteJpaController.findCarreraestudianteEntities();
    }

    public List<Carreraestudiante> obtenerCarrerasEstudiante() {
        return carreraestudianteJpaController.findCarreraestudianteEntities();
    }

    public void guardarCarreraEstudiante(Carreraestudiante addCarreraEstudiante) {
        carreraestudianteJpaController.create(addCarreraEstudiante);
    }

    public void elimimarCarreraEstudiante(Carreraestudiante carreraestudiante) {
        try {
            carreraestudianteJpaController.destroy(carreraestudiante.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("imtelPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void promoverSemestre(NivelCursoEstudiante nivel) {
        try {
            // inscripEstudianteMateriaJpaController.edit(nivel);
            nivelCursoEstudianteJpaController.edit(nivel);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<InscripEstudianteMateria> buscarTodasLasInscripcionesEstudiantes() {
        return inscripEstudianteMateriaJpaController.findInscripEstudianteMateriaEntities();
    }

    public void actualizarInscripcion(InscripEstudianteMateria inscripcion) {
        try {
            inscripEstudianteMateriaJpaController.edit(inscripcion);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        ;
    }

    public void crearCarrera(Carrera nuevaCarrera) {
        carreraJpaController.create(nuevaCarrera);
    }

    public Carrera buscarCarreraPorNombre(String nombreCarrera) {
        List<Carrera> listaCompletaCarrera =listarCarreraImtel();
        for(Carrera carrera: listaCompletaCarrera){
            if(carrera.getNombrecarrera().equalsIgnoreCase(nombreCarrera)){
               return carrera;
            }
        }
        return null;
    }

    public void actualizarNivelEstudiante(NivelCursoEstudiante nivel) {
        try {
            nivelCursoEstudianteJpaController.edit(nivel);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarCarrera(Carrera carrera) {
        try {
            carreraJpaController.edit(carrera);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Usuario> listarUsuarios() {
        return usuarioJpaController.findUsuarioEntities();
    }

   

}
