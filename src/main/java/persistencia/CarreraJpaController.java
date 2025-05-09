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
import logica.Carrera;
import logica.InscripEstudianteMateria;
import logica.Materias;
import logica.Carreraestudiante;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class CarreraJpaController implements Serializable {

    public CarreraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public CarreraJpaController() {
      this.emf=Persistence.createEntityManagerFactory("imtelPU");
   }
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carrera carrera) {
        if (carrera.getNivelCursoEstudianteList() == null) {
            carrera.setNivelCursoEstudianteList(new ArrayList<NivelCursoEstudiante>());
        }
        if (carrera.getInscripEstudianteMateriaList() == null) {
            carrera.setInscripEstudianteMateriaList(new ArrayList<InscripEstudianteMateria>());
        }
        if (carrera.getMateriasList() == null) {
            carrera.setMateriasList(new ArrayList<Materias>());
        }
        if (carrera.getCarreraestudianteList() == null) {
            carrera.setCarreraestudianteList(new ArrayList<Carreraestudiante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<NivelCursoEstudiante> attachedNivelCursoEstudianteList = new ArrayList<NivelCursoEstudiante>();
            for (NivelCursoEstudiante nivelCursoEstudianteListNivelCursoEstudianteToAttach : carrera.getNivelCursoEstudianteList()) {
                nivelCursoEstudianteListNivelCursoEstudianteToAttach = em.getReference(nivelCursoEstudianteListNivelCursoEstudianteToAttach.getClass(), nivelCursoEstudianteListNivelCursoEstudianteToAttach.getId());
                attachedNivelCursoEstudianteList.add(nivelCursoEstudianteListNivelCursoEstudianteToAttach);
            }
            carrera.setNivelCursoEstudianteList(attachedNivelCursoEstudianteList);
            List<InscripEstudianteMateria> attachedInscripEstudianteMateriaList = new ArrayList<InscripEstudianteMateria>();
            for (InscripEstudianteMateria inscripEstudianteMateriaListInscripEstudianteMateriaToAttach : carrera.getInscripEstudianteMateriaList()) {
                inscripEstudianteMateriaListInscripEstudianteMateriaToAttach = em.getReference(inscripEstudianteMateriaListInscripEstudianteMateriaToAttach.getClass(), inscripEstudianteMateriaListInscripEstudianteMateriaToAttach.getId());
                attachedInscripEstudianteMateriaList.add(inscripEstudianteMateriaListInscripEstudianteMateriaToAttach);
            }
            carrera.setInscripEstudianteMateriaList(attachedInscripEstudianteMateriaList);
            List<Materias> attachedMateriasList = new ArrayList<Materias>();
            for (Materias materiasListMateriasToAttach : carrera.getMateriasList()) {
                materiasListMateriasToAttach = em.getReference(materiasListMateriasToAttach.getClass(), materiasListMateriasToAttach.getIdMateria());
                attachedMateriasList.add(materiasListMateriasToAttach);
            }
            carrera.setMateriasList(attachedMateriasList);
            List<Carreraestudiante> attachedCarreraestudianteList = new ArrayList<Carreraestudiante>();
            for (Carreraestudiante carreraestudianteListCarreraestudianteToAttach : carrera.getCarreraestudianteList()) {
                carreraestudianteListCarreraestudianteToAttach = em.getReference(carreraestudianteListCarreraestudianteToAttach.getClass(), carreraestudianteListCarreraestudianteToAttach.getId());
                attachedCarreraestudianteList.add(carreraestudianteListCarreraestudianteToAttach);
            }
            carrera.setCarreraestudianteList(attachedCarreraestudianteList);
            em.persist(carrera);
            for (NivelCursoEstudiante nivelCursoEstudianteListNivelCursoEstudiante : carrera.getNivelCursoEstudianteList()) {
                Carrera oldIdCarreraOfNivelCursoEstudianteListNivelCursoEstudiante = nivelCursoEstudianteListNivelCursoEstudiante.getIdCarrera();
                nivelCursoEstudianteListNivelCursoEstudiante.setIdCarrera(carrera);
                nivelCursoEstudianteListNivelCursoEstudiante = em.merge(nivelCursoEstudianteListNivelCursoEstudiante);
                if (oldIdCarreraOfNivelCursoEstudianteListNivelCursoEstudiante != null) {
                    oldIdCarreraOfNivelCursoEstudianteListNivelCursoEstudiante.getNivelCursoEstudianteList().remove(nivelCursoEstudianteListNivelCursoEstudiante);
                    oldIdCarreraOfNivelCursoEstudianteListNivelCursoEstudiante = em.merge(oldIdCarreraOfNivelCursoEstudianteListNivelCursoEstudiante);
                }
            }
            for (InscripEstudianteMateria inscripEstudianteMateriaListInscripEstudianteMateria : carrera.getInscripEstudianteMateriaList()) {
                Carrera oldIdCarreraOfInscripEstudianteMateriaListInscripEstudianteMateria = inscripEstudianteMateriaListInscripEstudianteMateria.getIdCarrera();
                inscripEstudianteMateriaListInscripEstudianteMateria.setIdCarrera(carrera);
                inscripEstudianteMateriaListInscripEstudianteMateria = em.merge(inscripEstudianteMateriaListInscripEstudianteMateria);
                if (oldIdCarreraOfInscripEstudianteMateriaListInscripEstudianteMateria != null) {
                    oldIdCarreraOfInscripEstudianteMateriaListInscripEstudianteMateria.getInscripEstudianteMateriaList().remove(inscripEstudianteMateriaListInscripEstudianteMateria);
                    oldIdCarreraOfInscripEstudianteMateriaListInscripEstudianteMateria = em.merge(oldIdCarreraOfInscripEstudianteMateriaListInscripEstudianteMateria);
                }
            }
            for (Materias materiasListMaterias : carrera.getMateriasList()) {
                Carrera oldIdCarreraOfMateriasListMaterias = materiasListMaterias.getIdCarrera();
                materiasListMaterias.setIdCarrera(carrera);
                materiasListMaterias = em.merge(materiasListMaterias);
                if (oldIdCarreraOfMateriasListMaterias != null) {
                    oldIdCarreraOfMateriasListMaterias.getMateriasList().remove(materiasListMaterias);
                    oldIdCarreraOfMateriasListMaterias = em.merge(oldIdCarreraOfMateriasListMaterias);
                }
            }
            for (Carreraestudiante carreraestudianteListCarreraestudiante : carrera.getCarreraestudianteList()) {
                Carrera oldIdcarreraOfCarreraestudianteListCarreraestudiante = carreraestudianteListCarreraestudiante.getIdcarrera();
                carreraestudianteListCarreraestudiante.setIdcarrera(carrera);
                carreraestudianteListCarreraestudiante = em.merge(carreraestudianteListCarreraestudiante);
                if (oldIdcarreraOfCarreraestudianteListCarreraestudiante != null) {
                    oldIdcarreraOfCarreraestudianteListCarreraestudiante.getCarreraestudianteList().remove(carreraestudianteListCarreraestudiante);
                    oldIdcarreraOfCarreraestudianteListCarreraestudiante = em.merge(oldIdcarreraOfCarreraestudianteListCarreraestudiante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carrera carrera) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera persistentCarrera = em.find(Carrera.class, carrera.getId());
            List<NivelCursoEstudiante> nivelCursoEstudianteListOld = persistentCarrera.getNivelCursoEstudianteList();
            List<NivelCursoEstudiante> nivelCursoEstudianteListNew = carrera.getNivelCursoEstudianteList();
            List<InscripEstudianteMateria> inscripEstudianteMateriaListOld = persistentCarrera.getInscripEstudianteMateriaList();
            List<InscripEstudianteMateria> inscripEstudianteMateriaListNew = carrera.getInscripEstudianteMateriaList();
            List<Materias> materiasListOld = persistentCarrera.getMateriasList();
            List<Materias> materiasListNew = carrera.getMateriasList();
            List<Carreraestudiante> carreraestudianteListOld = persistentCarrera.getCarreraestudianteList();
            List<Carreraestudiante> carreraestudianteListNew = carrera.getCarreraestudianteList();
            List<String> illegalOrphanMessages = null;
            for (NivelCursoEstudiante nivelCursoEstudianteListOldNivelCursoEstudiante : nivelCursoEstudianteListOld) {
                if (!nivelCursoEstudianteListNew.contains(nivelCursoEstudianteListOldNivelCursoEstudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain NivelCursoEstudiante " + nivelCursoEstudianteListOldNivelCursoEstudiante + " since its idCarrera field is not nullable.");
                }
            }
            for (Materias materiasListOldMaterias : materiasListOld) {
                if (!materiasListNew.contains(materiasListOldMaterias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Materias " + materiasListOldMaterias + " since its idCarrera field is not nullable.");
                }
            }
            for (Carreraestudiante carreraestudianteListOldCarreraestudiante : carreraestudianteListOld) {
                if (!carreraestudianteListNew.contains(carreraestudianteListOldCarreraestudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Carreraestudiante " + carreraestudianteListOldCarreraestudiante + " since its idcarrera field is not nullable.");
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
            carrera.setNivelCursoEstudianteList(nivelCursoEstudianteListNew);
            List<InscripEstudianteMateria> attachedInscripEstudianteMateriaListNew = new ArrayList<InscripEstudianteMateria>();
            for (InscripEstudianteMateria inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach : inscripEstudianteMateriaListNew) {
                inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach = em.getReference(inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach.getClass(), inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach.getId());
                attachedInscripEstudianteMateriaListNew.add(inscripEstudianteMateriaListNewInscripEstudianteMateriaToAttach);
            }
            inscripEstudianteMateriaListNew = attachedInscripEstudianteMateriaListNew;
            carrera.setInscripEstudianteMateriaList(inscripEstudianteMateriaListNew);
            List<Materias> attachedMateriasListNew = new ArrayList<Materias>();
            for (Materias materiasListNewMateriasToAttach : materiasListNew) {
                materiasListNewMateriasToAttach = em.getReference(materiasListNewMateriasToAttach.getClass(), materiasListNewMateriasToAttach.getIdMateria());
                attachedMateriasListNew.add(materiasListNewMateriasToAttach);
            }
            materiasListNew = attachedMateriasListNew;
            carrera.setMateriasList(materiasListNew);
            List<Carreraestudiante> attachedCarreraestudianteListNew = new ArrayList<Carreraestudiante>();
            for (Carreraestudiante carreraestudianteListNewCarreraestudianteToAttach : carreraestudianteListNew) {
                carreraestudianteListNewCarreraestudianteToAttach = em.getReference(carreraestudianteListNewCarreraestudianteToAttach.getClass(), carreraestudianteListNewCarreraestudianteToAttach.getId());
                attachedCarreraestudianteListNew.add(carreraestudianteListNewCarreraestudianteToAttach);
            }
            carreraestudianteListNew = attachedCarreraestudianteListNew;
            carrera.setCarreraestudianteList(carreraestudianteListNew);
            carrera = em.merge(carrera);
            for (NivelCursoEstudiante nivelCursoEstudianteListNewNivelCursoEstudiante : nivelCursoEstudianteListNew) {
                if (!nivelCursoEstudianteListOld.contains(nivelCursoEstudianteListNewNivelCursoEstudiante)) {
                    Carrera oldIdCarreraOfNivelCursoEstudianteListNewNivelCursoEstudiante = nivelCursoEstudianteListNewNivelCursoEstudiante.getIdCarrera();
                    nivelCursoEstudianteListNewNivelCursoEstudiante.setIdCarrera(carrera);
                    nivelCursoEstudianteListNewNivelCursoEstudiante = em.merge(nivelCursoEstudianteListNewNivelCursoEstudiante);
                    if (oldIdCarreraOfNivelCursoEstudianteListNewNivelCursoEstudiante != null && !oldIdCarreraOfNivelCursoEstudianteListNewNivelCursoEstudiante.equals(carrera)) {
                        oldIdCarreraOfNivelCursoEstudianteListNewNivelCursoEstudiante.getNivelCursoEstudianteList().remove(nivelCursoEstudianteListNewNivelCursoEstudiante);
                        oldIdCarreraOfNivelCursoEstudianteListNewNivelCursoEstudiante = em.merge(oldIdCarreraOfNivelCursoEstudianteListNewNivelCursoEstudiante);
                    }
                }
            }
            for (InscripEstudianteMateria inscripEstudianteMateriaListOldInscripEstudianteMateria : inscripEstudianteMateriaListOld) {
                if (!inscripEstudianteMateriaListNew.contains(inscripEstudianteMateriaListOldInscripEstudianteMateria)) {
                    inscripEstudianteMateriaListOldInscripEstudianteMateria.setIdCarrera(null);
                    inscripEstudianteMateriaListOldInscripEstudianteMateria = em.merge(inscripEstudianteMateriaListOldInscripEstudianteMateria);
                }
            }
            for (InscripEstudianteMateria inscripEstudianteMateriaListNewInscripEstudianteMateria : inscripEstudianteMateriaListNew) {
                if (!inscripEstudianteMateriaListOld.contains(inscripEstudianteMateriaListNewInscripEstudianteMateria)) {
                    Carrera oldIdCarreraOfInscripEstudianteMateriaListNewInscripEstudianteMateria = inscripEstudianteMateriaListNewInscripEstudianteMateria.getIdCarrera();
                    inscripEstudianteMateriaListNewInscripEstudianteMateria.setIdCarrera(carrera);
                    inscripEstudianteMateriaListNewInscripEstudianteMateria = em.merge(inscripEstudianteMateriaListNewInscripEstudianteMateria);
                    if (oldIdCarreraOfInscripEstudianteMateriaListNewInscripEstudianteMateria != null && !oldIdCarreraOfInscripEstudianteMateriaListNewInscripEstudianteMateria.equals(carrera)) {
                        oldIdCarreraOfInscripEstudianteMateriaListNewInscripEstudianteMateria.getInscripEstudianteMateriaList().remove(inscripEstudianteMateriaListNewInscripEstudianteMateria);
                        oldIdCarreraOfInscripEstudianteMateriaListNewInscripEstudianteMateria = em.merge(oldIdCarreraOfInscripEstudianteMateriaListNewInscripEstudianteMateria);
                    }
                }
            }
            for (Materias materiasListNewMaterias : materiasListNew) {
                if (!materiasListOld.contains(materiasListNewMaterias)) {
                    Carrera oldIdCarreraOfMateriasListNewMaterias = materiasListNewMaterias.getIdCarrera();
                    materiasListNewMaterias.setIdCarrera(carrera);
                    materiasListNewMaterias = em.merge(materiasListNewMaterias);
                    if (oldIdCarreraOfMateriasListNewMaterias != null && !oldIdCarreraOfMateriasListNewMaterias.equals(carrera)) {
                        oldIdCarreraOfMateriasListNewMaterias.getMateriasList().remove(materiasListNewMaterias);
                        oldIdCarreraOfMateriasListNewMaterias = em.merge(oldIdCarreraOfMateriasListNewMaterias);
                    }
                }
            }
            for (Carreraestudiante carreraestudianteListNewCarreraestudiante : carreraestudianteListNew) {
                if (!carreraestudianteListOld.contains(carreraestudianteListNewCarreraestudiante)) {
                    Carrera oldIdcarreraOfCarreraestudianteListNewCarreraestudiante = carreraestudianteListNewCarreraestudiante.getIdcarrera();
                    carreraestudianteListNewCarreraestudiante.setIdcarrera(carrera);
                    carreraestudianteListNewCarreraestudiante = em.merge(carreraestudianteListNewCarreraestudiante);
                    if (oldIdcarreraOfCarreraestudianteListNewCarreraestudiante != null && !oldIdcarreraOfCarreraestudianteListNewCarreraestudiante.equals(carrera)) {
                        oldIdcarreraOfCarreraestudianteListNewCarreraestudiante.getCarreraestudianteList().remove(carreraestudianteListNewCarreraestudiante);
                        oldIdcarreraOfCarreraestudianteListNewCarreraestudiante = em.merge(oldIdcarreraOfCarreraestudianteListNewCarreraestudiante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = carrera.getId();
                if (findCarrera(id) == null) {
                    throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.");
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
            Carrera carrera;
            try {
                carrera = em.getReference(Carrera.class, id);
                carrera.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<NivelCursoEstudiante> nivelCursoEstudianteListOrphanCheck = carrera.getNivelCursoEstudianteList();
            for (NivelCursoEstudiante nivelCursoEstudianteListOrphanCheckNivelCursoEstudiante : nivelCursoEstudianteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Carrera (" + carrera + ") cannot be destroyed since the NivelCursoEstudiante " + nivelCursoEstudianteListOrphanCheckNivelCursoEstudiante + " in its nivelCursoEstudianteList field has a non-nullable idCarrera field.");
            }
            List<Materias> materiasListOrphanCheck = carrera.getMateriasList();
            for (Materias materiasListOrphanCheckMaterias : materiasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Carrera (" + carrera + ") cannot be destroyed since the Materias " + materiasListOrphanCheckMaterias + " in its materiasList field has a non-nullable idCarrera field.");
            }
            List<Carreraestudiante> carreraestudianteListOrphanCheck = carrera.getCarreraestudianteList();
            for (Carreraestudiante carreraestudianteListOrphanCheckCarreraestudiante : carreraestudianteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Carrera (" + carrera + ") cannot be destroyed since the Carreraestudiante " + carreraestudianteListOrphanCheckCarreraestudiante + " in its carreraestudianteList field has a non-nullable idcarrera field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<InscripEstudianteMateria> inscripEstudianteMateriaList = carrera.getInscripEstudianteMateriaList();
            for (InscripEstudianteMateria inscripEstudianteMateriaListInscripEstudianteMateria : inscripEstudianteMateriaList) {
                inscripEstudianteMateriaListInscripEstudianteMateria.setIdCarrera(null);
                inscripEstudianteMateriaListInscripEstudianteMateria = em.merge(inscripEstudianteMateriaListInscripEstudianteMateria);
            }
            em.remove(carrera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carrera> findCarreraEntities() {
        return findCarreraEntities(true, -1, -1);
    }

    public List<Carrera> findCarreraEntities(int maxResults, int firstResult) {
        return findCarreraEntities(false, maxResults, firstResult);
    }

    private List<Carrera> findCarreraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carrera.class));
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

    public Carrera findCarrera(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carrera.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarreraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carrera> rt = cq.from(Carrera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
