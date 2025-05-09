/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.Carrera;
import logica.InscripEstudianteMateria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logica.Materias;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class MateriasJpaController implements Serializable {

    public MateriasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public MateriasJpaController() {
      this.emf=Persistence.createEntityManagerFactory("imtelPU");
   
    }
    
    
    
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Materias materias) {
        if (materias.getInscripEstudianteMateriaList() == null) {
            materias.setInscripEstudianteMateriaList(new ArrayList<InscripEstudianteMateria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera idCarrera = materias.getIdCarrera();
            if (idCarrera != null) {
                idCarrera = em.getReference(idCarrera.getClass(), idCarrera.getId());
                materias.setIdCarrera(idCarrera);
            }
            List<InscripEstudianteMateria> attachedInscripEstudianteMateriaList = new ArrayList<InscripEstudianteMateria>();
            for (InscripEstudianteMateria inscripEstudianteMateriaListInscripEstudianteMateriaToAttach : materias.getInscripEstudianteMateriaList()) {
                inscripEstudianteMateriaListInscripEstudianteMateriaToAttach = em.getReference(inscripEstudianteMateriaListInscripEstudianteMateriaToAttach.getClass(), inscripEstudianteMateriaListInscripEstudianteMateriaToAttach.getId());
                attachedInscripEstudianteMateriaList.add(inscripEstudianteMateriaListInscripEstudianteMateriaToAttach);
            }
            materias.setInscripEstudianteMateriaList(attachedInscripEstudianteMateriaList);
            em.persist(materias);
            if (idCarrera != null) {
                idCarrera.getMateriasList().add(materias);
                idCarrera = em.merge(idCarrera);
            }
            for (InscripEstudianteMateria inscripEstudianteMateriaListInscripEstudianteMateria : materias.getInscripEstudianteMateriaList()) {
                Materias oldIdMateriaOfInscripEstudianteMateriaListInscripEstudianteMateria = inscripEstudianteMateriaListInscripEstudianteMateria.getIdMateria();
                inscripEstudianteMateriaListInscripEstudianteMateria.setIdMateria(materias);
                inscripEstudianteMateriaListInscripEstudianteMateria = em.merge(inscripEstudianteMateriaListInscripEstudianteMateria);
                if (oldIdMateriaOfInscripEstudianteMateriaListInscripEstudianteMateria != null) {
                    oldIdMateriaOfInscripEstudianteMateriaListInscripEstudianteMateria.getInscripEstudianteMateriaList().remove(inscripEstudianteMateriaListInscripEstudianteMateria);
                    oldIdMateriaOfInscripEstudianteMateriaListInscripEstudianteMateria = em.merge(oldIdMateriaOfInscripEstudianteMateriaListInscripEstudianteMateria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Materias materias) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materias persistentMaterias = em.find(Materias.class, materias.getIdMateria());
            Carrera idCarreraOld = persistentMaterias.getIdCarrera();
            Carrera idCarreraNew = materias.getIdCarrera();
            List<InscripEstudianteMateria> inscripEstudianteMateriaListOld = persistentMaterias.getInscripEstudianteMateriaList();
            List<InscripEstudianteMateria> inscripEstudianteMateriaListNew = materias.getInscripEstudianteMateriaList();
            List<String> illegalOrphanMessages = null;
            for (InscripEstudianteMateria inscripEstudianteMateriaListOldInscripEstudianteMateria : inscripEstudianteMateriaListOld) {
                if (!inscripEstudianteMateriaListNew.contains(inscripEstudianteMateriaListOldInscripEstudianteMateria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscripEstudianteMateria " + inscripEstudianteMateriaListOldInscripEstudianteMateria + " since its idMateria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCarreraNew != null) {
                idCarreraNew = em.getReference(idCarreraNew.getClass(), idCarreraNew.getId());
                materias.setIdCarrera(idCarreraNew);
            }
            List<InscripEstudianteMateria> attachedInscripEstudianteMateriaListNew = new ArrayList<InscripEstudianteMateria>();
            for (InscripEstudianteMateria inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach : inscripEstudianteMateriaListNew) {
                inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach = em.getReference(inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach.getClass(), inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach.getId());
                attachedInscripEstudianteMateriaListNew.add(inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach);
            }
            inscripEstudianteMateriaListNew = attachedInscripEstudianteMateriaListNew;
            materias.setInscripEstudianteMateriaList(inscripEstudianteMateriaListNew);
            materias = em.merge(materias);
            if (idCarreraOld != null && !idCarreraOld.equals(idCarreraNew)) {
                idCarreraOld.getMateriasList().remove(materias);
                idCarreraOld = em.merge(idCarreraOld);
            }
            if (idCarreraNew != null && !idCarreraNew.equals(idCarreraOld)) {
                idCarreraNew.getMateriasList().add(materias);
                idCarreraNew = em.merge(idCarreraNew);
            }
            for (InscripEstudianteMateria inscripEstudianteMateriaListNewInscripEstudianteMateria : inscripEstudianteMateriaListNew) {
                if (!inscripEstudianteMateriaListOld.contains(inscripEstudianteMateriaListNewInscripEstudianteMateria)) {
                    Materias oldIdMateriaOfInscripEstudianteMateriaListNewInscripEstudianteMateria = inscripEstudianteMateriaListNewInscripEstudianteMateria.getIdMateria();
                    inscripEstudianteMateriaListNewInscripEstudianteMateria.setIdMateria(materias);
                    inscripEstudianteMateriaListNewInscripEstudianteMateria = em.merge(inscripEstudianteMateriaListNewInscripEstudianteMateria);
                    if (oldIdMateriaOfInscripEstudianteMateriaListNewInscripEstudianteMateria != null && !oldIdMateriaOfInscripEstudianteMateriaListNewInscripEstudianteMateria.equals(materias)) {
                        oldIdMateriaOfInscripEstudianteMateriaListNewInscripEstudianteMateria.getInscripEstudianteMateriaList().remove(inscripEstudianteMateriaListNewInscripEstudianteMateria);
                        oldIdMateriaOfInscripEstudianteMateriaListNewInscripEstudianteMateria = em.merge(oldIdMateriaOfInscripEstudianteMateriaListNewInscripEstudianteMateria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = materias.getIdMateria();
                if (findMaterias(id) == null) {
                    throw new NonexistentEntityException("The materias with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materias materias;
            try {
                materias = em.getReference(Materias.class, id);
                materias.getIdMateria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materias with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<InscripEstudianteMateria> inscripEstudianteMateriaListOrphanCheck = materias.getInscripEstudianteMateriaList();
            for (InscripEstudianteMateria inscripEstudianteMateriaListOrphanCheckInscripEstudianteMateria : inscripEstudianteMateriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Materias (" + materias + ") cannot be destroyed since the InscripEstudianteMateria " + inscripEstudianteMateriaListOrphanCheckInscripEstudianteMateria + " in its inscripEstudianteMateriaList field has a non-nullable idMateria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Carrera idCarrera = materias.getIdCarrera();
            if (idCarrera != null) {
                idCarrera.getMateriasList().remove(materias);
                idCarrera = em.merge(idCarrera);
            }
            em.remove(materias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Materias> findMateriasEntities() {
        return findMateriasEntities(true, -1, -1);
    }

    public List<Materias> findMateriasEntities(int maxResults, int firstResult) {
        return findMateriasEntities(false, maxResults, firstResult);
    }

    private List<Materias> findMateriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Materias.class));
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

    public Materias findMaterias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Materias.class, id);
        } finally {
            em.close();
        }
    }

    public int getMateriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Materias> rt = cq.from(Materias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
