/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorBD;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import Modelo.Alumno;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Clase;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mauricio Juárez
 */
public class AlumnoJpaController implements Serializable {

    public AlumnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alumno alumno) {
        if (alumno.getClaseList() == null) {
            alumno.setClaseList(new ArrayList<Clase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Clase> attachedClaseList = new ArrayList<Clase>();
            for (Clase claseListClaseToAttach : alumno.getClaseList()) {
                claseListClaseToAttach = em.getReference(claseListClaseToAttach.getClass(), claseListClaseToAttach.getIdClase());
                attachedClaseList.add(claseListClaseToAttach);
            }
            alumno.setClaseList(attachedClaseList);
            em.persist(alumno);
            for (Clase claseListClase : alumno.getClaseList()) {
                Alumno oldIdAlumnoOfClaseListClase = claseListClase.getIdAlumno();
                claseListClase.setIdAlumno(alumno);
                claseListClase = em.merge(claseListClase);
                if (oldIdAlumnoOfClaseListClase != null) {
                    oldIdAlumnoOfClaseListClase.getClaseList().remove(claseListClase);
                    oldIdAlumnoOfClaseListClase = em.merge(oldIdAlumnoOfClaseListClase);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumno alumno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno persistentAlumno = em.find(Alumno.class, alumno.getIdAlumno());
            List<Clase> claseListOld = persistentAlumno.getClaseList();
            List<Clase> claseListNew = alumno.getClaseList();
            List<String> illegalOrphanMessages = null;
            for (Clase claseListOldClase : claseListOld) {
                if (!claseListNew.contains(claseListOldClase)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Clase " + claseListOldClase + " since its idAlumno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Clase> attachedClaseListNew = new ArrayList<Clase>();
            for (Clase claseListNewClaseToAttach : claseListNew) {
                claseListNewClaseToAttach = em.getReference(claseListNewClaseToAttach.getClass(), claseListNewClaseToAttach.getIdClase());
                attachedClaseListNew.add(claseListNewClaseToAttach);
            }
            claseListNew = attachedClaseListNew;
            alumno.setClaseList(claseListNew);
            alumno = em.merge(alumno);
            for (Clase claseListNewClase : claseListNew) {
                if (!claseListOld.contains(claseListNewClase)) {
                    Alumno oldIdAlumnoOfClaseListNewClase = claseListNewClase.getIdAlumno();
                    claseListNewClase.setIdAlumno(alumno);
                    claseListNewClase = em.merge(claseListNewClase);
                    if (oldIdAlumnoOfClaseListNewClase != null && !oldIdAlumnoOfClaseListNewClase.equals(alumno)) {
                        oldIdAlumnoOfClaseListNewClase.getClaseList().remove(claseListNewClase);
                        oldIdAlumnoOfClaseListNewClase = em.merge(oldIdAlumnoOfClaseListNewClase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alumno.getIdAlumno();
                if (findAlumno(id) == null) {
                    throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.");
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
            Alumno alumno;
            try {
                alumno = em.getReference(Alumno.class, id);
                alumno.getIdAlumno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Clase> claseListOrphanCheck = alumno.getClaseList();
            for (Clase claseListOrphanCheckClase : claseListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumno (" + alumno + ") cannot be destroyed since the Clase " + claseListOrphanCheckClase + " in its claseList field has a non-nullable idAlumno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(alumno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumno> findAlumnoEntities() {
        return findAlumnoEntities(true, -1, -1);
    }

    public List<Alumno> findAlumnoEntities(int maxResults, int firstResult) {
        return findAlumnoEntities(false, maxResults, firstResult);
    }

    private List<Alumno> findAlumnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumno.class));
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

    public Alumno findAlumno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumno.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumno> rt = cq.from(Alumno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Alumno> obtenerPorApellidos (String apellidos) {
        EntityManager em = getEntityManager();
        List<Alumno> a = em.createNamedQuery("Alumno.findByApellidos")
                .setParameter("apellidos", "%" + apellidos + "%").getResultList();
        return a;
    }
    
    public List<Alumno> obtenerPorMatricula (String matricula) {
        EntityManager em = getEntityManager();
        List<Alumno> a = em.createNamedQuery("Alumno.findByMatricula")
                .setParameter("matricula", "%" + matricula + "%").getResultList();
        return a;
    }
    
}
