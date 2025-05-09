/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.Carrera;
import logica.Estudiante;
import logica.InscripEstudianteMateria;
import logica.Materias;
import logica.Semestre;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class InscripEstudianteMateriaJpaController implements Serializable {

    public InscripEstudianteMateriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public InscripEstudianteMateriaJpaController() {
      this.emf=Persistence.createEntityManagerFactory("imtelPU");
   
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InscripEstudianteMateria inscripEstudianteMateria) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera idCarrera = inscripEstudianteMateria.getIdCarrera();
            if (idCarrera != null) {
                idCarrera = em.getReference(idCarrera.getClass(), idCarrera.getId());
                inscripEstudianteMateria.setIdCarrera(idCarrera);
            }
            Estudiante dniEstudiante = inscripEstudianteMateria.getDniEstudiante();
            if (dniEstudiante != null) {
                dniEstudiante = em.getReference(dniEstudiante.getClass(), dniEstudiante.getIdEstudiante());
                inscripEstudianteMateria.setDniEstudiante(dniEstudiante);
            }
            Materias idMateria = inscripEstudianteMateria.getIdMateria();
            if (idMateria != null) {
                idMateria = em.getReference(idMateria.getClass(), idMateria.getIdMateria());
                inscripEstudianteMateria.setIdMateria(idMateria);
            }
            Semestre idSemestre = inscripEstudianteMateria.getIdSemestre();
            if (idSemestre != null) {
                idSemestre = em.getReference(idSemestre.getClass(), idSemestre.getIdSemestre());
                inscripEstudianteMateria.setIdSemestre(idSemestre);
            }
            em.persist(inscripEstudianteMateria);
            if (idCarrera != null) {
                idCarrera.getInscripEstudianteMateriaList().add(inscripEstudianteMateria);
                idCarrera = em.merge(idCarrera);
            }
            if (dniEstudiante != null) {
                dniEstudiante.getInscripEstudianteMateriaList().add(inscripEstudianteMateria);
                dniEstudiante = em.merge(dniEstudiante);
            }
            if (idMateria != null) {
                idMateria.getInscripEstudianteMateriaList().add(inscripEstudianteMateria);
                idMateria = em.merge(idMateria);
            }
            if (idSemestre != null) {
                idSemestre.getInscripEstudianteMateriaList().add(inscripEstudianteMateria);
                idSemestre = em.merge(idSemestre);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InscripEstudianteMateria inscripEstudianteMateria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InscripEstudianteMateria persistentInscripEstudianteMateria = em.find(InscripEstudianteMateria.class, inscripEstudianteMateria.getId());
            Carrera idCarreraOld = persistentInscripEstudianteMateria.getIdCarrera();
            Carrera idCarreraNew = inscripEstudianteMateria.getIdCarrera();
            Estudiante dniEstudianteOld = persistentInscripEstudianteMateria.getDniEstudiante();
            Estudiante dniEstudianteNew = inscripEstudianteMateria.getDniEstudiante();
            Materias idMateriaOld = persistentInscripEstudianteMateria.getIdMateria();
            Materias idMateriaNew = inscripEstudianteMateria.getIdMateria();
            Semestre idSemestreOld = persistentInscripEstudianteMateria.getIdSemestre();
            Semestre idSemestreNew = inscripEstudianteMateria.getIdSemestre();
            if (idCarreraNew != null) {
                idCarreraNew = em.getReference(idCarreraNew.getClass(), idCarreraNew.getId());
                inscripEstudianteMateria.setIdCarrera(idCarreraNew);
            }
            if (dniEstudianteNew != null) {
                dniEstudianteNew = em.getReference(dniEstudianteNew.getClass(), dniEstudianteNew.getIdEstudiante());
                inscripEstudianteMateria.setDniEstudiante(dniEstudianteNew);
            }
            if (idMateriaNew != null) {
                idMateriaNew = em.getReference(idMateriaNew.getClass(), idMateriaNew.getIdMateria());
                inscripEstudianteMateria.setIdMateria(idMateriaNew);
            }
            if (idSemestreNew != null) {
                idSemestreNew = em.getReference(idSemestreNew.getClass(), idSemestreNew.getIdSemestre());
                inscripEstudianteMateria.setIdSemestre(idSemestreNew);
            }
            inscripEstudianteMateria = em.merge(inscripEstudianteMateria);
            if (idCarreraOld != null && !idCarreraOld.equals(idCarreraNew)) {
                idCarreraOld.getInscripEstudianteMateriaList().remove(inscripEstudianteMateria);
                idCarreraOld = em.merge(idCarreraOld);
            }
            if (idCarreraNew != null && !idCarreraNew.equals(idCarreraOld)) {
                idCarreraNew.getInscripEstudianteMateriaList().add(inscripEstudianteMateria);
                idCarreraNew = em.merge(idCarreraNew);
            }
            if (dniEstudianteOld != null && !dniEstudianteOld.equals(dniEstudianteNew)) {
                dniEstudianteOld.getInscripEstudianteMateriaList().remove(inscripEstudianteMateria);
                dniEstudianteOld = em.merge(dniEstudianteOld);
            }
            if (dniEstudianteNew != null && !dniEstudianteNew.equals(dniEstudianteOld)) {
                dniEstudianteNew.getInscripEstudianteMateriaList().add(inscripEstudianteMateria);
                dniEstudianteNew = em.merge(dniEstudianteNew);
            }
            if (idMateriaOld != null && !idMateriaOld.equals(idMateriaNew)) {
                idMateriaOld.getInscripEstudianteMateriaList().remove(inscripEstudianteMateria);
                idMateriaOld = em.merge(idMateriaOld);
            }
            if (idMateriaNew != null && !idMateriaNew.equals(idMateriaOld)) {
                idMateriaNew.getInscripEstudianteMateriaList().add(inscripEstudianteMateria);
                idMateriaNew = em.merge(idMateriaNew);
            }
            if (idSemestreOld != null && !idSemestreOld.equals(idSemestreNew)) {
                idSemestreOld.getInscripEstudianteMateriaList().remove(inscripEstudianteMateria);
                idSemestreOld = em.merge(idSemestreOld);
            }
            if (idSemestreNew != null && !idSemestreNew.equals(idSemestreOld)) {
                idSemestreNew.getInscripEstudianteMateriaList().add(inscripEstudianteMateria);
                idSemestreNew = em.merge(idSemestreNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inscripEstudianteMateria.getId();
                if (findInscripEstudianteMateria(id) == null) {
                    throw new NonexistentEntityException("The inscripEstudianteMateria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InscripEstudianteMateria inscripEstudianteMateria;
            try {
                inscripEstudianteMateria = em.getReference(InscripEstudianteMateria.class, id);
                inscripEstudianteMateria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscripEstudianteMateria with id " + id + " no longer exists.", enfe);
            }
            Carrera idCarrera = inscripEstudianteMateria.getIdCarrera();
            if (idCarrera != null) {
                idCarrera.getInscripEstudianteMateriaList().remove(inscripEstudianteMateria);
                idCarrera = em.merge(idCarrera);
            }
            Estudiante dniEstudiante = inscripEstudianteMateria.getDniEstudiante();
            if (dniEstudiante != null) {
                dniEstudiante.getInscripEstudianteMateriaList().remove(inscripEstudianteMateria);
                dniEstudiante = em.merge(dniEstudiante);
            }
            Materias idMateria = inscripEstudianteMateria.getIdMateria();
            if (idMateria != null) {
                idMateria.getInscripEstudianteMateriaList().remove(inscripEstudianteMateria);
                idMateria = em.merge(idMateria);
            }
            Semestre idSemestre = inscripEstudianteMateria.getIdSemestre();
            if (idSemestre != null) {
                idSemestre.getInscripEstudianteMateriaList().remove(inscripEstudianteMateria);
                idSemestre = em.merge(idSemestre);
            }
            em.remove(inscripEstudianteMateria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InscripEstudianteMateria> findInscripEstudianteMateriaEntities() {
        return findInscripEstudianteMateriaEntities(true, -1, -1);
    }

    public List<InscripEstudianteMateria> findInscripEstudianteMateriaEntities(int maxResults, int firstResult) {
        return findInscripEstudianteMateriaEntities(false, maxResults, firstResult);
    }

    private List<InscripEstudianteMateria> findInscripEstudianteMateriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InscripEstudianteMateria.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public InscripEstudianteMateria findInscripEstudianteMateria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InscripEstudianteMateria.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscripEstudianteMateriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InscripEstudianteMateria> rt = cq.from(InscripEstudianteMateria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
