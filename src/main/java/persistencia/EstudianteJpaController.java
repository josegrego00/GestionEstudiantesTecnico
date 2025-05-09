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
import logica.Carreraestudiante;
import logica.Estudiante;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class EstudianteJpaController implements Serializable {

    public EstudianteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EstudianteJpaController() {
      this.emf=Persistence.createEntityManagerFactory("imtelPU");
   
    }
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiante estudiante) {
        if (estudiante.getNivelCursoEstudianteList() == null) {
            estudiante.setNivelCursoEstudianteList(new ArrayList<NivelCursoEstudiante>());
        }
        if (estudiante.getInscripEstudianteMateriaList() == null) {
            estudiante.setInscripEstudianteMateriaList(new ArrayList<InscripEstudianteMateria>());
        }
        if (estudiante.getCarreraestudianteList() == null) {
            estudiante.setCarreraestudianteList(new ArrayList<Carreraestudiante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<NivelCursoEstudiante> attachedNivelCursoEstudianteList = new ArrayList<NivelCursoEstudiante>();
            for (NivelCursoEstudiante nivelCursoEstudianteListNivelCursoEstudianteToAttach : estudiante.getNivelCursoEstudianteList()) {
                nivelCursoEstudianteListNivelCursoEstudianteToAttach = em.getReference(nivelCursoEstudianteListNivelCursoEstudianteToAttach.getClass(), nivelCursoEstudianteListNivelCursoEstudianteToAttach.getId());
                attachedNivelCursoEstudianteList.add(nivelCursoEstudianteListNivelCursoEstudianteToAttach);
            }
            estudiante.setNivelCursoEstudianteList(attachedNivelCursoEstudianteList);
            List<InscripEstudianteMateria> attachedInscripEstudianteMateriaList = new ArrayList<InscripEstudianteMateria>();
            for (InscripEstudianteMateria inscripEstudianteMateriaListInscripEstudianteMateriaToAttach : estudiante.getInscripEstudianteMateriaList()) {
                inscripEstudianteMateriaListInscripEstudianteMateriaToAttach = em.getReference(inscripEstudianteMateriaListInscripEstudianteMateriaToAttach.getClass(), inscripEstudianteMateriaListInscripEstudianteMateriaToAttach.getId());
                attachedInscripEstudianteMateriaList.add(inscripEstudianteMateriaListInscripEstudianteMateriaToAttach);
            }
            estudiante.setInscripEstudianteMateriaList(attachedInscripEstudianteMateriaList);
            List<Carreraestudiante> attachedCarreraestudianteList = new ArrayList<Carreraestudiante>();
            for (Carreraestudiante carreraestudianteListCarreraestudianteToAttach : estudiante.getCarreraestudianteList()) {
                carreraestudianteListCarreraestudianteToAttach = em.getReference(carreraestudianteListCarreraestudianteToAttach.getClass(), carreraestudianteListCarreraestudianteToAttach.getId());
                attachedCarreraestudianteList.add(carreraestudianteListCarreraestudianteToAttach);
            }
            estudiante.setCarreraestudianteList(attachedCarreraestudianteList);
            em.persist(estudiante);
            for (NivelCursoEstudiante nivelCursoEstudianteListNivelCursoEstudiante : estudiante.getNivelCursoEstudianteList()) {
                Estudiante oldDniEstudianteOfNivelCursoEstudianteListNivelCursoEstudiante = nivelCursoEstudianteListNivelCursoEstudiante.getDniEstudiante();
                nivelCursoEstudianteListNivelCursoEstudiante.setDniEstudiante(estudiante);
                nivelCursoEstudianteListNivelCursoEstudiante = em.merge(nivelCursoEstudianteListNivelCursoEstudiante);
                if (oldDniEstudianteOfNivelCursoEstudianteListNivelCursoEstudiante != null) {
                    oldDniEstudianteOfNivelCursoEstudianteListNivelCursoEstudiante.getNivelCursoEstudianteList().remove(nivelCursoEstudianteListNivelCursoEstudiante);
                    oldDniEstudianteOfNivelCursoEstudianteListNivelCursoEstudiante = em.merge(oldDniEstudianteOfNivelCursoEstudianteListNivelCursoEstudiante);
                }
            }
            for (InscripEstudianteMateria inscripEstudianteMateriaListInscripEstudianteMateria : estudiante.getInscripEstudianteMateriaList()) {
                Estudiante oldDniEstudianteOfInscripEstudianteMateriaListInscripEstudianteMateria = inscripEstudianteMateriaListInscripEstudianteMateria.getDniEstudiante();
                inscripEstudianteMateriaListInscripEstudianteMateria.setDniEstudiante(estudiante);
                inscripEstudianteMateriaListInscripEstudianteMateria = em.merge(inscripEstudianteMateriaListInscripEstudianteMateria);
                if (oldDniEstudianteOfInscripEstudianteMateriaListInscripEstudianteMateria != null) {
                    oldDniEstudianteOfInscripEstudianteMateriaListInscripEstudianteMateria.getInscripEstudianteMateriaList().remove(inscripEstudianteMateriaListInscripEstudianteMateria);
                    oldDniEstudianteOfInscripEstudianteMateriaListInscripEstudianteMateria = em.merge(oldDniEstudianteOfInscripEstudianteMateriaListInscripEstudianteMateria);
                }
            }
            for (Carreraestudiante carreraestudianteListCarreraestudiante : estudiante.getCarreraestudianteList()) {
                Estudiante oldIdestudianteOfCarreraestudianteListCarreraestudiante = carreraestudianteListCarreraestudiante.getIdestudiante();
                carreraestudianteListCarreraestudiante.setIdestudiante(estudiante);
                carreraestudianteListCarreraestudiante = em.merge(carreraestudianteListCarreraestudiante);
                if (oldIdestudianteOfCarreraestudianteListCarreraestudiante != null) {
                    oldIdestudianteOfCarreraestudianteListCarreraestudiante.getCarreraestudianteList().remove(carreraestudianteListCarreraestudiante);
                    oldIdestudianteOfCarreraestudianteListCarreraestudiante = em.merge(oldIdestudianteOfCarreraestudianteListCarreraestudiante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudiante estudiante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante persistentEstudiante = em.find(Estudiante.class, estudiante.getIdEstudiante());
            List<NivelCursoEstudiante> nivelCursoEstudianteListOld = persistentEstudiante.getNivelCursoEstudianteList();
            List<NivelCursoEstudiante> nivelCursoEstudianteListNew = estudiante.getNivelCursoEstudianteList();
            List<InscripEstudianteMateria> inscripEstudianteMateriaListOld = persistentEstudiante.getInscripEstudianteMateriaList();
            List<InscripEstudianteMateria> inscripEstudianteMateriaListNew = estudiante.getInscripEstudianteMateriaList();
            List<Carreraestudiante> carreraestudianteListOld = persistentEstudiante.getCarreraestudianteList();
            List<Carreraestudiante> carreraestudianteListNew = estudiante.getCarreraestudianteList();
            List<String> illegalOrphanMessages = null;
            for (NivelCursoEstudiante nivelCursoEstudianteListOldNivelCursoEstudiante : nivelCursoEstudianteListOld) {
                if (!nivelCursoEstudianteListNew.contains(nivelCursoEstudianteListOldNivelCursoEstudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain NivelCursoEstudiante " + nivelCursoEstudianteListOldNivelCursoEstudiante + " since its dniEstudiante field is not nullable.");
                }
            }
            for (InscripEstudianteMateria inscripEstudianteMateriaListOldInscripEstudianteMateria : inscripEstudianteMateriaListOld) {
                if (!inscripEstudianteMateriaListNew.contains(inscripEstudianteMateriaListOldInscripEstudianteMateria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscripEstudianteMateria " + inscripEstudianteMateriaListOldInscripEstudianteMateria + " since its dniEstudiante field is not nullable.");
                }
            }
            for (Carreraestudiante carreraestudianteListOldCarreraestudiante : carreraestudianteListOld) {
                if (!carreraestudianteListNew.contains(carreraestudianteListOldCarreraestudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Carreraestudiante " + carreraestudianteListOldCarreraestudiante + " since its idestudiante field is not nullable.");
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
            estudiante.setNivelCursoEstudianteList(nivelCursoEstudianteListNew);
            List<InscripEstudianteMateria> attachedInscripEstudianteMateriaListNew = new ArrayList<InscripEstudianteMateria>();
            for (InscripEstudianteMateria inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach : inscripEstudianteMateriaListNew) {
                inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach = em.getReference(inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach.getClass(), inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach.getId());
                attachedInscripEstudianteMateriaListNew.add(inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach);
            }
            inscripEstudianteMateriaListNew = attachedInscripEstudianteMateriaListNew;
            estudiante.setInscripEstudianteMateriaList(inscripEstudianteMateriaListNew);
            List<Carreraestudiante> attachedCarreraestudianteListNew = new ArrayList<Carreraestudiante>();
            for (Carreraestudiante carreraestudianteListNewCarreraestudianteToAttach : carreraestudianteListNew) {
                carreraestudianteListNewCarreraestudianteToAttach = em.getReference(carreraestudianteListNewCarreraestudianteToAttach.getClass(), carreraestudianteListNewCarreraestudianteToAttach.getId());
                attachedCarreraestudianteListNew.add(carreraestudianteListNewCarreraestudianteToAttach);
            }
            carreraestudianteListNew = attachedCarreraestudianteListNew;
            estudiante.setCarreraestudianteList(carreraestudianteListNew);
            estudiante = em.merge(estudiante);
            for (NivelCursoEstudiante nivelCursoEstudianteListNewNivelCursoEstudiante : nivelCursoEstudianteListNew) {
                if (!nivelCursoEstudianteListOld.contains(nivelCursoEstudianteListNewNivelCursoEstudiante)) {
                    Estudiante oldDniEstudianteOfNivelCursoEstudianteListNewNivelCursoEstudiante = nivelCursoEstudianteListNewNivelCursoEstudiante.getDniEstudiante();
                    nivelCursoEstudianteListNewNivelCursoEstudiante.setDniEstudiante(estudiante);
                    nivelCursoEstudianteListNewNivelCursoEstudiante = em.merge(nivelCursoEstudianteListNewNivelCursoEstudiante);
                    if (oldDniEstudianteOfNivelCursoEstudianteListNewNivelCursoEstudiante != null && !oldDniEstudianteOfNivelCursoEstudianteListNewNivelCursoEstudiante.equals(estudiante)) {
                        oldDniEstudianteOfNivelCursoEstudianteListNewNivelCursoEstudiante.getNivelCursoEstudianteList().remove(nivelCursoEstudianteListNewNivelCursoEstudiante);
                        oldDniEstudianteOfNivelCursoEstudianteListNewNivelCursoEstudiante = em.merge(oldDniEstudianteOfNivelCursoEstudianteListNewNivelCursoEstudiante);
                    }
                }
            }
            for (InscripEstudianteMateria inscripEstudianteMateriaListNewInscripEstudianteMateria : inscripEstudianteMateriaListNew) {
                if (!inscripEstudianteMateriaListOld.contains(inscripEstudianteMateriaListNewInscripEstudianteMateria)) {
                    Estudiante oldDniEstudianteOfInscripEstudianteMateriaListNewInscripEstudianteMateria = inscripEstudianteMateriaListNewInscripEstudianteMateria.getDniEstudiante();
                    inscripEstudianteMateriaListNewInscripEstudianteMateria.setDniEstudiante(estudiante);
                    inscripEstudianteMateriaListNewInscripEstudianteMateria = em.merge(inscripEstudianteMateriaListNewInscripEstudianteMateria);
                    if (oldDniEstudianteOfInscripEstudianteMateriaListNewInscripEstudianteMateria != null && !oldDniEstudianteOfInscripEstudianteMateriaListNewInscripEstudianteMateria.equals(estudiante)) {
                        oldDniEstudianteOfInscripEstudianteMateriaListNewInscripEstudianteMateria.getInscripEstudianteMateriaList().remove(inscripEstudianteMateriaListNewInscripEstudianteMateria);
                        oldDniEstudianteOfInscripEstudianteMateriaListNewInscripEstudianteMateria = em.merge(oldDniEstudianteOfInscripEstudianteMateriaListNewInscripEstudianteMateria);
                    }
                }
            }
            for (Carreraestudiante carreraestudianteListNewCarreraestudiante : carreraestudianteListNew) {
                if (!carreraestudianteListOld.contains(carreraestudianteListNewCarreraestudiante)) {
                    Estudiante oldIdestudianteOfCarreraestudianteListNewCarreraestudiante = carreraestudianteListNewCarreraestudiante.getIdestudiante();
                    carreraestudianteListNewCarreraestudiante.setIdestudiante(estudiante);
                    carreraestudianteListNewCarreraestudiante = em.merge(carreraestudianteListNewCarreraestudiante);
                    if (oldIdestudianteOfCarreraestudianteListNewCarreraestudiante != null && !oldIdestudianteOfCarreraestudianteListNewCarreraestudiante.equals(estudiante)) {
                        oldIdestudianteOfCarreraestudianteListNewCarreraestudiante.getCarreraestudianteList().remove(carreraestudianteListNewCarreraestudiante);
                        oldIdestudianteOfCarreraestudianteListNewCarreraestudiante = em.merge(oldIdestudianteOfCarreraestudianteListNewCarreraestudiante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estudiante.getIdEstudiante();
                if (findEstudiante(id) == null) {
                    throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.");
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
            Estudiante estudiante;
            try {
                estudiante = em.getReference(Estudiante.class, id);
                estudiante.getIdEstudiante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<NivelCursoEstudiante> nivelCursoEstudianteListOrphanCheck = estudiante.getNivelCursoEstudianteList();
            for (NivelCursoEstudiante nivelCursoEstudianteListOrphanCheckNivelCursoEstudiante : nivelCursoEstudianteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the NivelCursoEstudiante " + nivelCursoEstudianteListOrphanCheckNivelCursoEstudiante + " in its nivelCursoEstudianteList field has a non-nullable dniEstudiante field.");
            }
            List<InscripEstudianteMateria> inscripEstudianteMateriaListOrphanCheck = estudiante.getInscripEstudianteMateriaList();
            for (InscripEstudianteMateria inscripEstudianteMateriaListOrphanCheckInscripEstudianteMateria : inscripEstudianteMateriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the InscripEstudianteMateria " + inscripEstudianteMateriaListOrphanCheckInscripEstudianteMateria + " in its inscripEstudianteMateriaList field has a non-nullable dniEstudiante field.");
            }
            List<Carreraestudiante> carreraestudianteListOrphanCheck = estudiante.getCarreraestudianteList();
            for (Carreraestudiante carreraestudianteListOrphanCheckCarreraestudiante : carreraestudianteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the Carreraestudiante " + carreraestudianteListOrphanCheckCarreraestudiante + " in its carreraestudianteList field has a non-nullable idestudiante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudiante> findEstudianteEntities() {
        return findEstudianteEntities(true, -1, -1);
    }

    public List<Estudiante> findEstudianteEntities(int maxResults, int firstResult) {
        return findEstudianteEntities(false, maxResults, firstResult);
    }

    private List<Estudiante> findEstudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudiante.class));
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

    public Estudiante findEstudiante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiante> rt = cq.from(Estudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
