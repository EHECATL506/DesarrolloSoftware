/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA.JPAController;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPA.ClasesEntidad.Maestro;
import JPA.ClasesEntidad.Clase;
import JPA.ClasesEntidad.Grupo;
import JPA.ClasesEntidad.Horario;
import JPA.JPAController.exceptions.IllegalOrphanException;
import JPA.JPAController.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mauricio Juárez
 */
public class GrupoJpaController implements Serializable {

    public GrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) {
        if (grupo.getHorarioList() == null) {
            grupo.setHorarioList(new ArrayList<Horario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro idMaestro = grupo.getIdMaestro();
            if (idMaestro != null) {
                idMaestro = em.getReference(idMaestro.getClass(), idMaestro.getId());
                grupo.setIdMaestro(idMaestro);
            }
            Clase clase = grupo.getClase();
            if (clase != null) {
                clase = em.getReference(clase.getClass(), clase.getIdClase());
                grupo.setClase(clase);
            }
            List<Horario> attachedHorarioList = new ArrayList<Horario>();
            for (Horario horarioListHorarioToAttach : grupo.getHorarioList()) {
                horarioListHorarioToAttach = em.getReference(horarioListHorarioToAttach.getClass(), horarioListHorarioToAttach.getIdHorario());
                attachedHorarioList.add(horarioListHorarioToAttach);
            }
            grupo.setHorarioList(attachedHorarioList);
            em.persist(grupo);
            if (idMaestro != null) {
                idMaestro.getGrupoList().add(grupo);
                idMaestro = em.merge(idMaestro);
            }
            if (clase != null) {
                Grupo oldIdGrupoOfClase = clase.getIdGrupo();
                if (oldIdGrupoOfClase != null) {
                    oldIdGrupoOfClase.setClase(null);
                    oldIdGrupoOfClase = em.merge(oldIdGrupoOfClase);
                }
                clase.setIdGrupo(grupo);
                clase = em.merge(clase);
            }
            for (Horario horarioListHorario : grupo.getHorarioList()) {
                Grupo oldIdGrupoOfHorarioListHorario = horarioListHorario.getIdGrupo();
                horarioListHorario.setIdGrupo(grupo);
                horarioListHorario = em.merge(horarioListHorario);
                if (oldIdGrupoOfHorarioListHorario != null) {
                    oldIdGrupoOfHorarioListHorario.getHorarioList().remove(horarioListHorario);
                    oldIdGrupoOfHorarioListHorario = em.merge(oldIdGrupoOfHorarioListHorario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getIdGrupo());
            Maestro idMaestroOld = persistentGrupo.getIdMaestro();
            Maestro idMaestroNew = grupo.getIdMaestro();
            Clase claseOld = persistentGrupo.getClase();
            Clase claseNew = grupo.getClase();
            List<Horario> horarioListOld = persistentGrupo.getHorarioList();
            List<Horario> horarioListNew = grupo.getHorarioList();
            List<String> illegalOrphanMessages = null;
            if (claseOld != null && !claseOld.equals(claseNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Clase " + claseOld + " since its idGrupo field is not nullable.");
            }
            for (Horario horarioListOldHorario : horarioListOld) {
                if (!horarioListNew.contains(horarioListOldHorario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Horario " + horarioListOldHorario + " since its idGrupo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idMaestroNew != null) {
                idMaestroNew = em.getReference(idMaestroNew.getClass(), idMaestroNew.getId());
                grupo.setIdMaestro(idMaestroNew);
            }
            if (claseNew != null) {
                claseNew = em.getReference(claseNew.getClass(), claseNew.getIdClase());
                grupo.setClase(claseNew);
            }
            List<Horario> attachedHorarioListNew = new ArrayList<Horario>();
            for (Horario horarioListNewHorarioToAttach : horarioListNew) {
                horarioListNewHorarioToAttach = em.getReference(horarioListNewHorarioToAttach.getClass(), horarioListNewHorarioToAttach.getIdHorario());
                attachedHorarioListNew.add(horarioListNewHorarioToAttach);
            }
            horarioListNew = attachedHorarioListNew;
            grupo.setHorarioList(horarioListNew);
            grupo = em.merge(grupo);
            if (idMaestroOld != null && !idMaestroOld.equals(idMaestroNew)) {
                idMaestroOld.getGrupoList().remove(grupo);
                idMaestroOld = em.merge(idMaestroOld);
            }
            if (idMaestroNew != null && !idMaestroNew.equals(idMaestroOld)) {
                idMaestroNew.getGrupoList().add(grupo);
                idMaestroNew = em.merge(idMaestroNew);
            }
            if (claseNew != null && !claseNew.equals(claseOld)) {
                Grupo oldIdGrupoOfClase = claseNew.getIdGrupo();
                if (oldIdGrupoOfClase != null) {
                    oldIdGrupoOfClase.setClase(null);
                    oldIdGrupoOfClase = em.merge(oldIdGrupoOfClase);
                }
                claseNew.setIdGrupo(grupo);
                claseNew = em.merge(claseNew);
            }
            for (Horario horarioListNewHorario : horarioListNew) {
                if (!horarioListOld.contains(horarioListNewHorario)) {
                    Grupo oldIdGrupoOfHorarioListNewHorario = horarioListNewHorario.getIdGrupo();
                    horarioListNewHorario.setIdGrupo(grupo);
                    horarioListNewHorario = em.merge(horarioListNewHorario);
                    if (oldIdGrupoOfHorarioListNewHorario != null && !oldIdGrupoOfHorarioListNewHorario.equals(grupo)) {
                        oldIdGrupoOfHorarioListNewHorario.getHorarioList().remove(horarioListNewHorario);
                        oldIdGrupoOfHorarioListNewHorario = em.merge(oldIdGrupoOfHorarioListNewHorario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupo.getIdGrupo();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
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
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getIdGrupo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Clase claseOrphanCheck = grupo.getClase();
            if (claseOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the Clase " + claseOrphanCheck + " in its clase field has a non-nullable idGrupo field.");
            }
            List<Horario> horarioListOrphanCheck = grupo.getHorarioList();
            for (Horario horarioListOrphanCheckHorario : horarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the Horario " + horarioListOrphanCheckHorario + " in its horarioList field has a non-nullable idGrupo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Maestro idMaestro = grupo.getIdMaestro();
            if (idMaestro != null) {
                idMaestro.getGrupoList().remove(grupo);
                idMaestro = em.merge(idMaestro);
            }
            em.remove(grupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
