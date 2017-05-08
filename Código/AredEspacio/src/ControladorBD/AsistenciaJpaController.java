/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorBD;

import ControladorBD.exceptions.NonexistentEntityException;
import Modelo.Asistencia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Clase;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mauricio
 */
public class AsistenciaJpaController implements Serializable {

    public AsistenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asistencia asistencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clase idClase = asistencia.getIdClase();
            if (idClase != null) {
                idClase = em.getReference(idClase.getClass(), idClase.getIdClase());
                asistencia.setIdClase(idClase);
            }
            em.persist(asistencia);
            if (idClase != null) {
                idClase.getAsistenciaList().add(asistencia);
                idClase = em.merge(idClase);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asistencia asistencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asistencia persistentAsistencia = em.find(Asistencia.class, asistencia.getIdAsistencia());
            Clase idClaseOld = persistentAsistencia.getIdClase();
            Clase idClaseNew = asistencia.getIdClase();
            if (idClaseNew != null) {
                idClaseNew = em.getReference(idClaseNew.getClass(), idClaseNew.getIdClase());
                asistencia.setIdClase(idClaseNew);
            }
            asistencia = em.merge(asistencia);
            if (idClaseOld != null && !idClaseOld.equals(idClaseNew)) {
                idClaseOld.getAsistenciaList().remove(asistencia);
                idClaseOld = em.merge(idClaseOld);
            }
            if (idClaseNew != null && !idClaseNew.equals(idClaseOld)) {
                idClaseNew.getAsistenciaList().add(asistencia);
                idClaseNew = em.merge(idClaseNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistencia.getIdAsistencia();
                if (findAsistencia(id) == null) {
                    throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.");
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
            Asistencia asistencia;
            try {
                asistencia = em.getReference(Asistencia.class, id);
                asistencia.getIdAsistencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.", enfe);
            }
            Clase idClase = asistencia.getIdClase();
            if (idClase != null) {
                idClase.getAsistenciaList().remove(asistencia);
                idClase = em.merge(idClase);
            }
            em.remove(asistencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asistencia> findAsistenciaEntities() {
        return findAsistenciaEntities(true, -1, -1);
    }

    public List<Asistencia> findAsistenciaEntities(int maxResults, int firstResult) {
        return findAsistenciaEntities(false, maxResults, firstResult);
    }

    private List<Asistencia> findAsistenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asistencia.class));
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

    public Asistencia findAsistencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asistencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asistencia> rt = cq.from(Asistencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
