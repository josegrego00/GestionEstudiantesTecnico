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
import logica.NivelCursoEstudiante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logica.InscripEstudianteMateria;
import logica.Semestre;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class SemestreJpaController implements Serializable {

    public SemestreJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public SemestreJpaController() {
      this.emf=Persistence.createEntityManagerFactory("imtelPU");
   
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Semestre semestre) {
        if (semestre.getNivelCursoEstudianteList() == null) {
            semestre.setNivelCursoEstudianteList(new ArrayList<NivelCursoEstudiante>());
        }
        if (semestre.getInscripEstudianteMateriaList() == null) {
            semestre.setInscripEstudianteMateriaList(new ArrayList<InscripEstudianteMateria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<NivelCursoEstudiante> attachedNivelCursoEstudianteList = new ArrayList<NivelCursoEstudiante>();
            for (NivelCursoEstudiante nivelCursoEstudianteListNivelCursoEstudianteToAttach : semestre.getNivelCursoEstudianteList()) {
                nivelCursoEstudianteListNivelCursoEstudianteToAttach = em.getReference(nivelCursoEstudianteListNivelCursoEstudianteToAttach.getClass(), nivelCursoEstudianteListNivelCursoEstudianteToAttach.getId());
                attachedNivelCursoEstudianteList.add(nivelCursoEstudianteListNivelCursoEstudianteToAttach);
            }
            semestre.setNivelCursoEstudianteList(attachedNivelCursoEstudianteList);
            List<InscripEstudianteMateria> attachedInscripEstudianteMateriaList = new ArrayList<InscripEstudianteMateria>();
            for (InscripEstudianteMateria inscripEstudianteMateriaListInscripEstudianteMateriaToAttach : semestre.getInscripEstudianteMateriaList()) {
                inscripEstudianteMateriaListInscripEstudianteMateriaToAttach = em.getReference(inscripEstudianteMateriaListInscripEstudianteMateriaToAttach.getClass(), inscripEstudianteMateriaListInscripEstudianteMateriaToAttach.getId());
                attachedInscripEstudianteMateriaList.add(inscripEstudianteMateriaListInscripEstudianteMateriaToAttach);
            }
            semestre.setInscripEstudianteMateriaList(attachedInscripEstudianteMateriaList);
            em.persist(semestre);
            for (NivelCursoEstudiante nivelCursoEstudianteListNivelCursoEstudiante : semestre.getNivelCursoEstudianteList()) {
                Semestre oldIdSemestreOfNivelCursoEstudianteListNivelCursoEstudiante = nivelCursoEstudianteListNivelCursoEstudiante.getIdSemestre();
                nivelCursoEstudianteListNivelCursoEstudiante.setIdSemestre(semestre);
                nivelCursoEstudianteListNivelCursoEstudiante = em.merge(nivelCursoEstudianteListNivelCursoEstudiante);
                if (oldIdSemestreOfNivelCursoEstudianteListNivelCursoEstudiante != null) {
                    oldIdSemestreOfNivelCursoEstudianteListNivelCursoEstudiante.getNivelCursoEstudianteList().remove(nivelCursoEstudianteListNivelCursoEstudiante);
                    oldIdSemestreOfNivelCursoEstudianteListNivelCursoEstudiante = em.merge(oldIdSemestreOfNivelCursoEstudianteListNivelCursoEstudiante);
                }
            }
            for (InscripEstudianteMateria inscripEstudianteMateriaListInscripEstudianteMateria : semestre.getInscripEstudianteMateriaList()) {
                Semestre oldIdSemestreOfInscripEstudianteMateriaListInscripEstudianteMateria = inscripEstudianteMateriaListInscripEstudianteMateria.getIdSemestre();
                inscripEstudianteMateriaListInscripEstudianteMateria.setIdSemestre(semestre);
                inscripEstudianteMateriaListInscripEstudianteMateria = em.merge(inscripEstudianteMateriaListInscripEstudianteMateria);
                if (oldIdSemestreOfInscripEstudianteMateriaListInscripEstudianteMateria != null) {
                    oldIdSemestreOfInscripEstudianteMateriaListInscripEstudianteMateria.getInscripEstudianteMateriaList().remove(inscripEstudianteMateriaListInscripEstudianteMateria);
                    oldIdSemestreOfInscripEstudianteMateriaListInscripEstudianteMateria = em.merge(oldIdSemestreOfInscripEstudianteMateriaListInscripEstudianteMateria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Semestre semestre) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Semestre persistentSemestre = em.find(Semestre.class, semestre.getIdSemestre());
            List<NivelCursoEstudiante> nivelCursoEstudianteListOld = persistentSemestre.getNivelCursoEstudianteList();
            List<NivelCursoEstudiante> nivelCursoEstudianteListNew = semestre.getNivelCursoEstudianteList();
            List<InscripEstudianteMateria> inscripEstudianteMateriaListOld = persistentSemestre.getInscripEstudianteMateriaList();
            List<InscripEstudianteMateria> inscripEstudianteMateriaListNew = semestre.getInscripEstudianteMateriaList();
            List<String> illegalOrphanMessages = null;
            for (NivelCursoEstudiante nivelCursoEstudianteListOldNivelCursoEstudiante : nivelCursoEstudianteListOld) {
                if (!nivelCursoEstudianteListNew.contains(nivelCursoEstudianteListOldNivelCursoEstudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain NivelCursoEstudiante " + nivelCursoEstudianteListOldNivelCursoEstudiante + " since its idSemestre field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<NivelCursoEstudiante> attachedNivelCursoEstudianteListNew = new ArrayList<NivelCursoEstudiante>();
            for (NivelCursoEstudiante nivelCursoEstudianteListNewNivelCursoEstudianteToAttach : nivelCursoEstudianteListNew) {
                nivelCursoEstudianteListNewNivelCursoEstudianteToAttach = em.getReference(nivelCursoEstudianteListNewNivelCursoEstudianteToAttach.getClass(), nivelCursoEstudianteListNewNivelCursoEstudianteToAttach.getId());
                attachedNivelCursoEstudianteListNew.add(nivelCursoEstudianteListNewNivelCursoEstudianteToAttach);
            }
            nivelCursoEstudianteListNew = attachedNivelCursoEstudianteListNew;
            semestre.setNivelCursoEstudianteList(nivelCursoEstudianteListNew);
            List<InscripEstudianteMateria> attachedInscripEstudianteMateriaListNew = new ArrayList<InscripEstudianteMateria>();
            for (InscripEstudianteMateria inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach : inscripEstudianteMateriaListNew) {
                inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach = em.getReference(inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach.getClass(), inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach.getId());
                attachedInscripEstudianteMateriaListNew.add(inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach);
            }
            inscripEstudianteMateriaListNew = attachedInscripEstudianteMateriaListNew;
            semestre.setInscripEstudianteMateriaList(inscripEstudianteMateriaListNew);
            semestre = em.merge(semestre);
            for (NivelCursoEstudiante nivelCursoEstudianteListNewNivelCursoEstudiante : nivelCursoEstudianteListNew) {
                if (!nivelCursoEstudianteListOld.contains(nivelCursoEstudianteListNewNivelCursoEstudiante)) {
                    Semestre oldIdSemestreOfNivelCursoEstudianteListNewNivelCursoEstudiante = nivelCursoEstudianteListNewNivelCursoEstudiante.getIdSemestre();
                    nivelCursoEstudianteListNewNivelCursoEstudiante.setIdSemestre(semestre);
                    nivelCursoEstudianteListNewNivelCursoEstudiante = em.merge(nivelCursoEstudianteListNewNivelCursoEstudiante);
                    if (oldIdSemestreOfNivelCursoEstudianteListNewNivelCursoEstudiante != null && !oldIdSemestreOfNivelCursoEstudianteListNewNivelCursoEstudiante.equals(semestre)) {
                        oldIdSemestreOfNivelCursoEstudianteListNewNivelCursoEstudiante.getNivelCursoEstudianteList().remove(nivelCursoEstudianteListNewNivelCursoEstudiante);
                        oldIdSemestreOfNivelCursoEstudianteListNewNivelCursoEstudiante = em.merge(oldIdSemestreOfNivelCursoEstudianteListNewNivelCursoEstudiante);
                    }
                }
            }
            for (InscripEstudianteMateria inscripEstudianteMateriaListOldInscripEstudianteMateria : inscripEstudianteMateriaListOld) {
                if (!inscripEstudianteMateriaListNew.contains(inscripEstudianteMateriaListOldInscripEstudianteMateria)) {
                    inscripEstudianteMateriaListOldInscripEstudianteMateria.setIdSemestre(null);
                    inscripEstudianteMateriaListOldInscripEstudianteMateria = em.merge(inscripEstudianteMateriaListOldInscripEstudianteMateria);
                }
            }
            for (InscripEstudianteMateria inscripEstudianteMateriaListNewInscripEstudianteMateria : inscripEstudianteMateriaListNew) {
                if (!inscripEstudianteMateriaListOld.contains(inscripEstudianteMateriaListNewInscripEstudianteMateria)) {
                    Semestre oldIdSemestreOfInscripEstudianteMateriaListNewInscripEstudianteMateria = inscripEstudianteMateriaListNewInscripEstudianteMateria.getIdSemestre();
                    inscripEstudianteMateriaListNewInscripEstudianteMateria.setIdSemestre(semestre);
                    inscripEstudianteMateriaListNewInscripEstudianteMateria = em.merge(inscripEstudianteMateriaListNewInscripEstudianteMateria);
                    if (oldIdSemestreOfInscripEstudianteMateriaListNewInscripEstudianteMateria != null && !oldIdSemestreOfInscripEstudianteMateriaListNewInscripEstudianteMateria.equals(semestre)) {
                        oldIdSemestreOfInscripEstudianteMateriaListNewInscripEstudianteMateria.getInscripEstudianteMateriaList().remove(inscripEstudianteMateriaListNewInscripEstudianteMateria);
                        oldIdSemestreOfInscripEstudianteMateriaListNewInscripEstudianteMateria = em.merge(oldIdSemestreOfInscripEstudianteMateriaListNewInscripEstudianteMateria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = semestre.getIdSemestre();
                if (findSemestre(id) == null) {
                    throw new NonexistentEntityException("The semestre with id " + id + " no longer exists.");
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
            Semestre semestre;
            try {
                semestre = em.getReference(Semestre.class, id);
                semestre.getIdSemestre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The semestre with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<NivelCursoEstudiante> nivelCursoEstudianteListOrphanCheck = semestre.getNivelCursoEstudianteList();
            for (NivelCursoEstudiante nivelCursoEstudianteListOrphanCheckNivelCursoEstudiante : nivelCursoEstudianteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Semestre (" + semestre + ") cannot be destroyed since the NivelCursoEstudiante " + nivelCursoEstudianteListOrphanCheckNivelCursoEstudiante + " in its nivelCursoEstudianteList field has a non-nullable idSemestre field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<InscripEstudianteMateria> inscripEstudianteMateriaList = semestre.getInscripEstudianteMateriaList();
            for (InscripEstudianteMateria inscripEstudianteMateriaListInscripEstudianteMateria : inscripEstudianteMateriaList) {
                inscripEstudianteMateriaListInscripEstudianteMateria.setIdSemestre(null);
                inscripEstudianteMateriaListInscripEstudianteMateria = em.merge(inscripEstudianteMateriaListInscripEstudianteMateria);
            }
            em.remove(semestre);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Semestre> findSemestreEntities() {
        return findSemestreEntities(true, -1, -1);
    }

    public List<Semestre> findSemestreEntities(int maxResults, int firstResult) {
        return findSemestreEntities(false, maxResults, firstResult);
    }

    private List<Semestre> findSemestreEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Semestre.class));
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

    public Semestre findSemestre(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Semestre.class, id);
        } finally {
            em.close();
        }
    }

    public int getSemestreCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Semestre> rt = cq.from(Semestre.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
