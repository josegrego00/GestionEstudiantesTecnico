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
import logica.NivelCursoEstudiante;
import logica.Semestre;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class NivelCursoEstudianteJpaController implements Serializable {

    public NivelCursoEstudianteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public NivelCursoEstudianteJpaController() {
      this.emf=Persistence.createEntityManagerFactory("imtelPU");
   
    }
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NivelCursoEstudiante nivelCursoEstudiante) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera idCarrera = nivelCursoEstudiante.getIdCarrera();
            if (idCarrera != null) {
                idCarrera = em.getReference(idCarrera.getClass(), idCarrera.getId());
                nivelCursoEstudiante.setIdCarrera(idCarrera);
            }
            Estudiante dniEstudiante = nivelCursoEstudiante.getDniEstudiante();
            if (dniEstudiante != null) {
                dniEstudiante = em.getReference(dniEstudiante.getClass(), dniEstudiante.getIdEstudiante());
                nivelCursoEstudiante.setDniEstudiante(dniEstudiante);
            }
            Semestre idSemestre = nivelCursoEstudiante.getIdSemestre();
            if (idSemestre != null) {
                idSemestre = em.getReference(idSemestre.getClass(), idSemestre.getIdSemestre());
                nivelCursoEstudiante.setIdSemestre(idSemestre);
            }
            em.persist(nivelCursoEstudiante);
            if (idCarrera != null) {
                idCarrera.getNivelCursoEstudianteList().add(nivelCursoEstudiante);
                idCarrera = em.merge(idCarrera);
            }
            if (dniEstudiante != null) {
                dniEstudiante.getNivelCursoEstudianteList().add(nivelCursoEstudiante);
                dniEstudiante = em.merge(dniEstudiante);
            }
            if (idSemestre != null) {
                idSemestre.getNivelCursoEstudianteList().add(nivelCursoEstudiante);
                idSemestre = em.merge(idSemestre);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NivelCursoEstudiante nivelCursoEstudiante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NivelCursoEstudiante persistentNivelCursoEstudiante = em.find(NivelCursoEstudiante.class, nivelCursoEstudiante.getId());
            Carrera idCarreraOld = persistentNivelCursoEstudiante.getIdCarrera();
            Carrera idCarreraNew = nivelCursoEstudiante.getIdCarrera();
            Estudiante dniEstudianteOld = persistentNivelCursoEstudiante.getDniEstudiante();
            Estudiante dniEstudianteNew = nivelCursoEstudiante.getDniEstudiante();
            Semestre idSemestreOld = persistentNivelCursoEstudiante.getIdSemestre();
            Semestre idSemestreNew = nivelCursoEstudiante.getIdSemestre();
            if (idCarreraNew != null) {
                idCarreraNew = em.getReference(idCarreraNew.getClass(), idCarreraNew.getId());
                nivelCursoEstudiante.setIdCarrera(idCarreraNew);
            }
            if (dniEstudianteNew != null) {
                dniEstudianteNew = em.getReference(dniEstudianteNew.getClass(), dniEstudianteNew.getIdEstudiante());
                nivelCursoEstudiante.setDniEstudiante(dniEstudianteNew);
            }
            if (idSemestreNew != null) {
                idSemestreNew = em.getReference(idSemestreNew.getClass(), idSemestreNew.getIdSemestre());
                nivelCursoEstudiante.setIdSemestre(idSemestreNew);
            }
            nivelCursoEstudiante = em.merge(nivelCursoEstudiante);
            if (idCarreraOld != null && !idCarreraOld.equals(idCarreraNew)) {
                idCarreraOld.getNivelCursoEstudianteList().remove(nivelCursoEstudiante);
                idCarreraOld = em.merge(idCarreraOld);
            }
            if (idCarreraNew != null && !idCarreraNew.equals(idCarreraOld)) {
                idCarreraNew.getNivelCursoEstudianteList().add(nivelCursoEstudiante);
                idCarreraNew = em.merge(idCarreraNew);
            }
            if (dniEstudianteOld != null && !dniEstudianteOld.equals(dniEstudianteNew)) {
                dniEstudianteOld.getNivelCursoEstudianteList().remove(nivelCursoEstudiante);
                dniEstudianteOld = em.merge(dniEstudianteOld);
            }
            if (dniEstudianteNew != null && !dniEstudianteNew.equals(dniEstudianteOld)) {
                dniEstudianteNew.getNivelCursoEstudianteList().add(nivelCursoEstudiante);
                dniEstudianteNew = em.merge(dniEstudianteNew);
            }
            if (idSemestreOld != null && !idSemestreOld.equals(idSemestreNew)) {
                idSemestreOld.getNivelCursoEstudianteList().remove(nivelCursoEstudiante);
                idSemestreOld = em.merge(idSemestreOld);
            }
            if (idSemestreNew != null && !idSemestreNew.equals(idSemestreOld)) {
                idSemestreNew.getNivelCursoEstudianteList().add(nivelCursoEstudiante);
                idSemestreNew = em.merge(idSemestreNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nivelCursoEstudiante.getId();
                if (findNivelCursoEstudiante(id) == null) {
                    throw new NonexistentEntityException("The nivelCursoEstudiante with id " + id + " no longer exists.");
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
            NivelCursoEstudiante nivelCursoEstudiante;
            try {
                nivelCursoEstudiante = em.getReference(NivelCursoEstudiante.class, id);
                nivelCursoEstudiante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nivelCursoEstudiante with id " + id + " no longer exists.", enfe);
            }
            Carrera idCarrera = nivelCursoEstudiante.getIdCarrera();
            if (idCarrera != null) {
                idCarrera.getNivelCursoEstudianteList().remove(nivelCursoEstudiante);
                idCarrera = em.merge(idCarrera);
            }
            Estudiante dniEstudiante = nivelCursoEstudiante.getDniEstudiante();
            if (dniEstudiante != null) {
                dniEstudiante.getNivelCursoEstudianteList().remove(nivelCursoEstudiante);
                dniEstudiante = em.merge(dniEstudiante);
            }
            Semestre idSemestre = nivelCursoEstudiante.getIdSemestre();
            if (idSemestre != null) {
                idSemestre.getNivelCursoEstudianteList().remove(nivelCursoEstudiante);
                idSemestre = em.merge(idSemestre);
            }
            em.remove(nivelCursoEstudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NivelCursoEstudiante> findNivelCursoEstudianteEntities() {
        return findNivelCursoEstudianteEntities(true, -1, -1);
    }

    public List<NivelCursoEstudiante> findNivelCursoEstudianteEntities(int maxResults, int firstResult) {
        return findNivelCursoEstudianteEntities(false, maxResults, firstResult);
    }

    private List<NivelCursoEstudiante> findNivelCursoEstudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NivelCursoEstudiante.class));
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

    public NivelCursoEstudiante findNivelCursoEstudiante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NivelCursoEstudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getNivelCursoEstudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NivelCursoEstudiante> rt = cq.from(NivelCursoEstudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
