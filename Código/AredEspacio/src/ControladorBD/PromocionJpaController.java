/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorBD;

import ControladorBD.exceptions.NonexistentEntityException;
import Modelo.Promocion;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Mauricio
 */
public class PromocionJpaController implements Serializable {

    public PromocionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promocion promocion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(promocion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Promocion promocion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            promocion = em.merge(promocion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = promocion.getIdPromocion();
                if (findPromocion(id) == null) {
                    throw new NonexistentEntityException("The promocion with id " + id + " no longer exists.");
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
            Promocion promocion;
            try {
                promocion = em.getReference(Promocion.class, id);
                promocion.getIdPromocion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promocion with id " + id + " no longer exists.", enfe);
            }
            em.remove(promocion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Promocion> findPromocionEntities() {
        return findPromocionEntities(true, -1, -1);
    }

    public List<Promocion> findPromocionEntities(int maxResults, int firstResult) {
        return findPromocionEntities(false, maxResults, firstResult);
    }

    private List<Promocion> findPromocionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Promocion.class));
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

    public Promocion findPromocion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Promocion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromocionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Promocion> rt = cq.from(Promocion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
