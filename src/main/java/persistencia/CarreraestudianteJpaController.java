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
import logica.Carreraestudiante;
import logica.Estudiante;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class CarreraestudianteJpaController implements Serializable {

    public CarreraestudianteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public CarreraestudianteJpaController() {
      this.emf=Persistence.createEntityManagerFactory("imtelPU");
   
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carreraestudiante carreraestudiante) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera idcarrera = carreraestudiante.getIdcarrera();
            if (idcarrera != null) {
                idcarrera = em.getReference(idcarrera.getClass(), idcarrera.getId());
                carreraestudiante.setIdcarrera(idcarrera);
            }
            Estudiante idestudiante = carreraestudiante.getIdestudiante();
            if (idestudiante != null) {
                idestudiante = em.getReference(idestudiante.getClass(), idestudiante.getIdEstudiante());
                carreraestudiante.setIdestudiante(idestudiante);
            }
            em.persist(carreraestudiante);
            if (idcarrera != null) {
                idcarrera.getCarreraestudianteList().add(carreraestudiante);
                idcarrera = em.merge(idcarrera);
            }
            if (idestudiante != null) {
                idestudiante.getCarreraestudianteList().add(carreraestudiante);
                idestudiante = em.merge(idestudiante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carreraestudiante carreraestudiante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carreraestudiante persistentCarreraestudiante = em.find(Carreraestudiante.class, carreraestudiante.getId());
            Carrera idcarreraOld = persistentCarreraestudiante.getIdcarrera();
            Carrera idcarreraNew = carreraestudiante.getIdcarrera();
            Estudiante idestudianteOld = persistentCarreraestudiante.getIdestudiante();
            Estudiante idestudianteNew = carreraestudiante.getIdestudiante();
            if (idcarreraNew != null) {
                idcarreraNew = em.getReference(idcarreraNew.getClass(), idcarreraNew.getId());
                carreraestudiante.setIdcarrera(idcarreraNew);
            }
            if (idestudianteNew != null) {
                idestudianteNew = em.getReference(idestudianteNew.getClass(), idestudianteNew.getIdEstudiante());
                carreraestudiante.setIdestudiante(idestudianteNew);
            }
            carreraestudiante = em.merge(carreraestudiante);
            if (idcarreraOld != null && !idcarreraOld.equals(idcarreraNew)) {
                idcarreraOld.getCarreraestudianteList().remove(carreraestudiante);
                idcarreraOld = em.merge(idcarreraOld);
            }
            if (idcarreraNew != null && !idcarreraNew.equals(idcarreraOld)) {
                idcarreraNew.getCarreraestudianteList().add(carreraestudiante);
                idcarreraNew = em.merge(idcarreraNew);
            }
            if (idestudianteOld != null && !idestudianteOld.equals(idestudianteNew)) {
                idestudianteOld.getCarreraestudianteList().remove(carreraestudiante);
                idestudianteOld = em.merge(idestudianteOld);
            }
            if (idestudianteNew != null && !idestudianteNew.equals(idestudianteOld)) {
                idestudianteNew.getCarreraestudianteList().add(carreraestudiante);
                idestudianteNew = em.merge(idestudianteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = carreraestudiante.getId();
                if (findCarreraestudiante(id) == null) {
                    throw new NonexistentEntityException("The carreraestudiante with id " + id + " no longer exists.");
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
            Carreraestudiante carreraestudiante;
            try {
                carreraestudiante = em.getReference(Carreraestudiante.class, id);
                carreraestudiante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carreraestudiante with id " + id + " no longer exists.", enfe);
            }
            Carrera idcarrera = carreraestudiante.getIdcarrera();
            if (idcarrera != null) {
                idcarrera.getCarreraestudianteList().remove(carreraestudiante);
                idcarrera = em.merge(idcarrera);
            }
            Estudiante idestudiante = carreraestudiante.getIdestudiante();
            if (idestudiante != null) {
                idestudiante.getCarreraestudianteList().remove(carreraestudiante);
                idestudiante = em.merge(idestudiante);
            }
            em.remove(carreraestudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carreraestudiante> findCarreraestudianteEntities() {
        return findCarreraestudianteEntities(true, -1, -1);
    }

    public List<Carreraestudiante> findCarreraestudianteEntities(int maxResults, int firstResult) {
        return findCarreraestudianteEntities(false, maxResults, firstResult);
    }

    private List<Carreraestudiante> findCarreraestudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carreraestudiante.class));
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

    public Carreraestudiante findCarreraestudiante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carreraestudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarreraestudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carreraestudiante> rt = cq.from(Carreraestudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
